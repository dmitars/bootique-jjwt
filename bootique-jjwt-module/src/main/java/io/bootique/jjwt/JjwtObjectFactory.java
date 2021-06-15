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
import io.bootique.jjwt.keystore.JwtParserProvider;
import io.bootique.jjwt.model.Parser;
import io.bootique.keystore.KeystoreObjectsFactory;
import io.jsonwebtoken.JwtParser;

import java.util.Objects;

@BQConfig
public class JjwtObjectFactory {
    private KeystoreObjectsFactory keystoreObjectsFactory;
    private Parser parser;


    public Parser getParser() {
        return parser;
    }

    @BQConfigProperty
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public JjwtObjectFactory createFactory(KeystoreObjectsFactory factory){
        Objects.requireNonNull(factory,"Keystores configuration not found");
        this.keystoreObjectsFactory = factory;
        return this;
    }

    public JwtParser configJwtParser() {
        Objects.requireNonNull(parser, "Parser configuration not found");

        JwtParserProvider loader = new JwtParserProvider(parser);
        return loader.createParser(keystoreObjectsFactory.loadedKeystores());
    }
}
