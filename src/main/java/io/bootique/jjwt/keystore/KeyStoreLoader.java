package io.bootique.jjwt.keystore;

import io.bootique.jjwt.model.KeyStoreMetadata;
import io.bootique.jjwt.model.Parser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

public class KeyStoreLoader {
    @Inject
    private Map<String, KeyStoreMetadata> keystores;

    private final Parser parser;

    public KeyStoreLoader(Parser parser) {
        this.parser = parser;
    }

    public JwtBuilder createJwtBuilder() throws IOException,
            CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (!keystores.containsKey(parser.getKeyStore()))
            throw new RuntimeException("keystore with name [" + parser.getKeyStore() + "] not found!");

        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStoreMetadata keyStoreMetadata = keystores.get(parser.getKeyStore());

            char[] password = keyStoreMetadata.getPassword().toCharArray();

            ks.load(new FileInputStream(keyStoreMetadata.getLocation()), password);
            return builder(ks, password);
        } catch (KeyStoreException e) {
            //ignored
        }

        return null;
    }

    private JwtBuilder builder(KeyStore keyStore, char[] password) throws KeyStoreException,
            UnrecoverableKeyException, NoSuchAlgorithmException {
        if (!keyStore.containsAlias(parser.getKeyAlias()))
            throw new RuntimeException("key alias [" + parser.getKeyAlias() + "] not found in provided keystore");

        return Jwts.builder()
                .setIssuer(parser.getRequireIssuer())
                .signWith(keyStore.getKey(parser.getKeyAlias(), password));
    }
}
