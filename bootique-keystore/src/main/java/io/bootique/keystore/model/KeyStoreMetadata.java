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

package io.bootique.keystore.model;

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import io.bootique.resource.ResourceFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Objects;

@BQConfig
public class KeyStoreMetadata {
    private ResourceFactory location;
    private String password;
    private String type;
    private String provider;

    @BQConfigProperty
    public void setLocation(ResourceFactory location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    @BQConfigProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    @BQConfigProperty
    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    @BQConfigProperty
    public void setProvider(String provider) {
        this.provider = provider;
    }

    private KeyStore toKeyStore() {
        try {
            String storeType = type == null ? "JKS" : type;
            return provider == null ? KeyStore.getInstance(storeType) : KeyStore.getInstance(storeType, provider);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Incorrect type: " + e.getMessage());
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("Provider not found: "+ provider);
        }
    }

    public ExtendedKeystore toLoadedExtendedKeyStore() {
        Objects.requireNonNull(password, "Cannot find password; add password property to keystore in configs");
        KeyStore keyStore = toKeyStore();
        loadKeyStore(keyStore);
        return new ExtendedKeystore(password, keyStore);
    }

    private void loadKeyStore(KeyStore keyStore) {
        try {
            Objects.requireNonNull(location,"Location not found; add location property to keystore in configs");
            keyStore.load(new FileInputStream(location.getUrl().getFile()), password.toCharArray());
        } catch (IOException e) {
            throw new RuntimeException("Error with keystore file: " + e.getMessage());
        } catch (CertificateException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Keystore loading error: " + e.getMessage());
        }
    }
}
