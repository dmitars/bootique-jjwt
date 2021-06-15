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

package io.bootique.keystore;

import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.junit5.BQApp;
import io.bootique.junit5.BQTest;
import io.bootique.keystore.model.ExtendedKeystore;
import org.junit.jupiter.api.Test;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@BQTest
public class KeyStoreLoadingIT {

    @BQApp(skipRun = true)
    static final BQRuntime app = Bootique
            .app("--config=classpath:io/bootique/keystore/test.yml")
            .autoLoadModules()
            .moduleProvider(new KeystoreModuleProvider())
            .createRuntime();

    private Map<String, ExtendedKeystore> keystores;


    @Test
    public void testKsLoading() {
        keystores = app.getInstance(KeystoreObjectsFactory.class).loadedKeystores();
        assertEquals(3, keystores.size());
        checkKeyStoreWith("ks1", "passOfKs1", "JKS", "mykey");
        checkKeyStoreWith("ks2", "passOfKs2", "JKS", "mykey");
        checkKeyStoreWith("ks3", "passOfKs3", "JKS", "mykey");
    }

    private void checkKeyStoreWith(String name, String password, String type, String alias) {
        assertTrue(keystores.containsKey(name));
        ExtendedKeystore extendedKeystore = keystores.get(name);
        assertEquals(extendedKeystore.getProvidedPassword(), password);
        assertTrue(extendedKeystore.getKeyStore().getType().equalsIgnoreCase(type));
        try {
            KeyStore.Entry entry = extendedKeystore.getKeyStore()
                    .getEntry(alias, new KeyStore.PasswordProtection(password.toCharArray()));
            assertNotNull(entry);
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            e.printStackTrace();
        }
    }
}
