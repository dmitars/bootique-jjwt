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

import java.security.KeyStore;

public class ExtendedKeystore{
    private String providedPassword;
    private KeyStore keyStore;

    public ExtendedKeystore(String providedPassword, KeyStore keyStore) {
        this.providedPassword = providedPassword;
        this.keyStore = keyStore;
    }

    public String getProvidedPassword() {
        return providedPassword;
    }

    public void setProvidedPassword(String providedPassword) {
        this.providedPassword = providedPassword;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }
}
