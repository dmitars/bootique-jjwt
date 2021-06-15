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
import io.bootique.junit5.BQTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@BQTest
public class KeyStoreLoadingErrorsIT {
    private BQRuntime app;


    private void initAndStartAppWithConfigFileName(String name){
        app = Bootique
                .app("--config=classpath:io/bootique/keystore/error-configs/"+name)
                .autoLoadModules()
                .moduleProvider(new KeystoreModuleProvider())
                .createRuntime();
    }

    @Test
    public void testKsWithoutKeystoresConfig() {
        initAndStartAppWithConfigFileName("without-keystores.yml");
        assertThrows(RuntimeException.class,()->app.getInstance(KeystoreObjectsFactory.class).loadedKeystores(),
                "Keystores configuration not found");
    }

    @Test
    public void testKsWithoutKeystoreLocation() {
        initAndStartAppWithConfigFileName("without-location.yml");
        assertThrows(RuntimeException.class,()->app.getInstance(KeystoreObjectsFactory.class).loadedKeystores(),
                "Location not found; add location property to keystore in configs");
    }

    @Test
    public void testKsWithoutKeystorePassword() {
        initAndStartAppWithConfigFileName("without-password.yml");
        assertThrows(RuntimeException.class,()->app.getInstance(KeystoreObjectsFactory.class).loadedKeystores(),
                "Cannot find password; add password property to keystore in configs");
    }

    @Test
    public void testKsWithIncorrectKeystoreType() {
        initAndStartAppWithConfigFileName("incorrect-type.yml");
        try{
            app.getInstance(KeystoreObjectsFactory.class).loadedKeystores();
            fail();
        }catch (RuntimeException e){
            assertTrue(e.getMessage().startsWith("Incorrect type: "));
        }
    }

    @Test
    public void testKsWithIncorrectKeystoreProvider() {
        initAndStartAppWithConfigFileName("incorrect-provider.yml");
        try{
            app.getInstance(KeystoreObjectsFactory.class).loadedKeystores();
            fail();
        }catch (RuntimeException e){
            assertTrue(e.getMessage().startsWith("Provider not found:"));
        }
    }
}
