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

import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Binder;
import io.bootique.di.Injector;
import io.bootique.di.Provides;
import io.bootique.keystore.ExtendedKeystore;
import io.bootique.type.TypeRef;
import io.jsonwebtoken.JwtParser;

import javax.inject.Singleton;
import java.util.Map;

public class JjwtModule extends ConfigModule {

    @Singleton
    @Provides
    JwtParser provideJwtParser(ConfigurationFactory configFactory,
                               Map<String, ExtendedKeystore> keyStores,
                               Injector injector) {
        ParserFactory parserFactory = configFactory.config(new TypeRef<ParserFactory>() {
        }, "jjwt.parser");
        return parserFactory.createParser(keyStores, injector);
    }

    /**
     * Returns an instance of {@link JJwtModuleExtender} used by downstream modules to load custom extensions to the
     * JjwtModule. Should be invoked from a downstream Module's "configure" method.
     *
     * @param binder DI binder passed to the Module that invokes this method.
     * @return an instance of {@link JJwtModuleExtender} that can be used to load custom extensions to the JjwtModule.
     */
    public static JJwtModuleExtender extend(Binder binder) {
        return new JJwtModuleExtender(binder);
    }

    @Override
    public void configure(Binder binder) {
        JjwtModule.extend(binder)
                .initAllExtensions();
    }
}
