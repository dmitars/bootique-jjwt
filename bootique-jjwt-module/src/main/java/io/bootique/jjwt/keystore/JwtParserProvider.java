package io.bootique.jjwt.keystore;

import io.bootique.jjwt.model.Parser;
import io.bootique.keystore.model.ExtendedKeystore;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

public class JwtParserProvider {

    private final Parser parserConfiguration;

    public JwtParserProvider(Parser parserConfiguration) {
        this.parserConfiguration = parserConfiguration;
    }

    public JwtParser createParser(Map<String, ExtendedKeystore> keystores) {
        if (!keystores.containsKey(parserConfiguration.getKeyStore()))
            throw new RuntimeException("keystore with name [" + parserConfiguration.getKeyStore() + "] not found!");

        try {
            ExtendedKeystore extendedKeystore = keystores.get(parserConfiguration.getKeyStore());
            return createParserFor(extendedKeystore);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Incorrect keystore configuration: " + e.getMessage());
        }
    }


    private JwtParser createParserFor(ExtendedKeystore extendedKeystore) throws KeyStoreException {
        KeyStore keyStore = extendedKeystore.getKeyStore();
        if (!keyStore.containsAlias(parserConfiguration.getKeyAlias()))
            throw new RuntimeException("key alias [" + parserConfiguration.getKeyAlias() + "] not found in provided keystore");

        char[] password = extendedKeystore.getProvidedPassword().toCharArray();
        try {
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder()
                    .setSigningKey(keyStore.getKey(parserConfiguration.getKeyAlias(), password))
                    .requireIssuer(parserConfiguration.getRequireIssuer())
                    .requireSubject(parserConfiguration.getSubject())
                    .requireAudience(parserConfiguration.getAudience())
                    .requireExpiration(parserConfiguration.getExpiration())
                    .requireId(parserConfiguration.getId())
                    .setAllowedClockSkewSeconds(parserConfiguration.getAllowedClockSkewSeconds());
            return jwtParserBuilder.build();
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RuntimeException("Error with reading keystore: " + e.getMessage());
        }
    }
}
