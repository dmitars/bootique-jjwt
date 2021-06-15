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
import io.bootique.jjwt.model.KeyStoreMetadata;
import io.bootique.jjwt.model.Parser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;

@BQConfig
public class JjwtObjectFactory {

    private Parser parser;
    private Map<String, KeyStoreMetadata> keystores;


    public Parser getParser() {
        return parser;
    }

    @BQConfigProperty
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @BQConfigProperty
    public void setKeystores(Map<String, KeyStoreMetadata> keystores) {
        this.keystores = keystores;
    }

    public Map<String, KeyStoreMetadata> getKeystores() {
        return keystores;
    }

    public JwtBuilder createJwtParser() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Objects.requireNonNull(parser);
        KeyStore ks = KeyStore.getInstance("JKS");

        if (!keystores.containsKey(parser.getKeyStore()))
            throw new RuntimeException("keystore with name [" + parser.getKeyStore() + "] not found!");

        KeyStoreMetadata keyStoreMetadata = getKeystores().get(parser.getKeyStore());

        char[] password = keyStoreMetadata.getPassword().toCharArray();

        ks.load(new FileInputStream(keyStoreMetadata.getLocation()), password);
        if (!ks.containsAlias(parser.getKeyAlias()))
            throw new RuntimeException("key alias [" + parser.getKeyAlias() + "] not found in provided keystore");

        return Jwts.builder()
                .setIssuer(parser.getRequireIssuer())
                .signWith(ks.getKey(parser.getKeyAlias(), password));
    }
}
