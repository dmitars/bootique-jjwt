/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.jjwt;

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import io.bootique.di.Injector;
import io.bootique.keystore.ExtendedKeystore;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Deserializer;

import java.security.*;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@BQConfig
public class ParserFactory {
    private String keyStore;
    private String keyAlias;

    private JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder();

    /**
     * Sets an requireIssuer property for the Parser object, which will be transferred to JwtParser.
     *
     * @param requireIssuer a requireIssuer property of JwtParser.
     */
    @BQConfigProperty("requireIssuer property of JwtParser")
    public void setRequireIssuer(String requireIssuer) {
        jwtParserBuilder.requireIssuer(requireIssuer);
    }

    /**
     * Sets keystore name from configs for the Parser object, which will be transferred to JwtParser.
     *
     * @param keyStore a name of keystore in configs, which JwtParser should use.
     */
    @BQConfigProperty("keystore name from configuration file")
    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Sets an alias of key in keystore, which JwtParser should use.
     *
     * @param keyAlias an alias of key for JwtParser.
     */
    @BQConfigProperty("alias of key in keystore")
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    /**
     * Sets an requireSubject property for the Parser object, which will be transferred to JwtParser.
     *
     * @param subject requireSubject property of JwtParser.
     */
    @BQConfigProperty("requireSubject property of JwtParser")
    public void setSubject(String subject) {
        jwtParserBuilder.requireSubject(subject);
    }

    /**
     * Sets an requireAudience property for the Parser object, which will be transferred to JwtParser.
     *
     * @param audience requireAudience property of JwtParser.
     */
    @BQConfigProperty("requireExpiration property of JwtParser")
    public void setAudience(String audience) {
        jwtParserBuilder.requireAudience(audience);
    }

    /**
     * Sets an requireExpiration property for the Parser object, which will be transferred to JwtParser.
     *
     * @param expiration requireExpiration property of JwtParser.
     */
    @BQConfigProperty("requireExpiration property of JwtParser")
    public void setExpiration(Date expiration) {
        jwtParserBuilder.requireExpiration(expiration);
    }

    /**
     * Sets an requireId property for the Parser object, which will be transferred to JwtParser.
     *
     * @param id requireId property of JwtParser.
     */
    @BQConfigProperty("requireId property of JwtParser")
    public void setId(String id) {
        jwtParserBuilder.requireId(id);
    }

    /**
     * Sets an allowedClockSkewSeconds property for the Parser object, which will be transferred to JwtParser.
     *
     * @param allowedClockSkewSeconds allowedClockSkewSeconds property of JwtParser.
     */
    @BQConfigProperty("allowedClockSkewSeconds property of JwtParser. 0 by default")
    public void setAllowedClockSkewSeconds(long allowedClockSkewSeconds) {
        jwtParserBuilder.setAllowedClockSkewSeconds(allowedClockSkewSeconds);
    }

    public JwtParser createParser(Map<String, ExtendedKeystore> keyStores, Injector injector) {
        try {
            ExtendedKeystore extendedKeystore = extendedKeystoreOf(keyStores);
            return parserBuilderFor(extendedKeystore, injector).build();
        } catch (KeyStoreException e) {
            throw new RuntimeException("Incorrect keystore configuration: " + e.getMessage());
        }
    }

    private ExtendedKeystore extendedKeystoreOf(Map<String, ExtendedKeystore> keyStores) {
        if (!keyStores.containsKey(keyStore))
            throw new RuntimeException("keystore with name [" + keyStores + "] not found!");

        return keyStores.get(keyStore);
    }

    private JwtParserBuilder parserBuilderFor(ExtendedKeystore extendedKeystore, Injector injector) throws KeyStoreException {
        Key key = getKeyFor(extendedKeystore);
        jwtParserBuilder = jwtParserBuilder.setSigningKey(key);

        ParserBehaviourResolver behaviourResolver = new ParserBehaviourResolver(injector);

        Optional<SigningKeyResolver> resolver = behaviourResolver.keyResolver();
        Optional<CompressionCodecResolver> compressionCodecResolver = behaviourResolver.compressionCodecResolver();
        Optional<Deserializer<Map<String, ?>>> deserializer = behaviourResolver.deserializer();
        Optional<Decoder<String, byte[]>> decoder = behaviourResolver.decoder();

        decoder.ifPresent(stringDecoder -> jwtParserBuilder = jwtParserBuilder.base64UrlDecodeWith(stringDecoder));
        deserializer.ifPresent(mapDeserializer -> jwtParserBuilder = jwtParserBuilder.deserializeJsonWith(mapDeserializer));
        resolver.ifPresent(signingKeyResolver -> jwtParserBuilder = jwtParserBuilder.setSigningKeyResolver(signingKeyResolver));
        compressionCodecResolver.ifPresent(codecResolver -> jwtParserBuilder = jwtParserBuilder.setCompressionCodecResolver(codecResolver));

        return jwtParserBuilder;
    }

    private Key getKeyFor(ExtendedKeystore extendedKeystore) throws KeyStoreException {
        KeyStore keyStore = extendedKeystore.getKeyStore();
        if (!keyStore.containsAlias(keyAlias))
            throw new RuntimeException("key alias [" + keyAlias + "] not found in provided keystore");

        char[] password = extendedKeystore.getProvidedPassword().toCharArray();
        return getKeyWith(keyStore, password);
    }

    private Key getKeyWith(KeyStore keyStore, char[] password) throws KeyStoreException {
        try {
            return keyStore.getKey(keyAlias, password);
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RuntimeException("Error with reading keystore: " + e.getMessage());
        }
    }
}
