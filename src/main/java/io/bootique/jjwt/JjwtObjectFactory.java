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
import io.bootique.di.Provides;
import io.bootique.jjwt.keystore.KeyStoreLoader;
import io.bootique.jjwt.model.KeyStoreMetadata;
import io.bootique.jjwt.model.Parser;
import io.jsonwebtoken.JwtBuilder;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.Objects;

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

    @Provides
    public Map<String, KeyStoreMetadata> getKeystores() {
        return keystores;
    }

    public JwtBuilder createJwtBuilder() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Objects.requireNonNull(parser);

        KeyStoreLoader loader = new KeyStoreLoader(parser);
        return loader.createJwtBuilder();
    }
}
