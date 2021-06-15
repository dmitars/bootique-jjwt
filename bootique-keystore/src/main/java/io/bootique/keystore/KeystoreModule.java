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

import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Provides;
import io.bootique.type.TypeRef;

import javax.inject.Singleton;
import java.util.Map;
import java.util.stream.Collectors;

public class KeystoreModule extends ConfigModule {
    private Map<String, ExtendedKeystore> parsedKeyStores;

    /**
     * Loads key stores from selected in config files and provides map, where key is name of keystore,
     * value - object with keystore and its password.
     */
    @Singleton
    @Provides
    Map<String, ExtendedKeystore> provideExtendedKeyStores(ConfigurationFactory configurationFactory) {
        Map<String, KeyStoreMetadataFactory> keyStores = configurationFactory
                .config(new TypeRef<Map<String, KeyStoreMetadataFactory>>() {
                }, "keystore");


        return parsedKeyStores != null ? parsedKeyStores : (parsedKeyStores = keyStores.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toLoadedExtendedKeyStore())
                ));
    }
}
