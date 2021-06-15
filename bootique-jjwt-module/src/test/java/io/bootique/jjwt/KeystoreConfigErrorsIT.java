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

import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.junit5.BQTest;
import io.jsonwebtoken.JwtParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@BQTest
public class KeystoreConfigErrorsIT {
    private BQRuntime app;


    private void initAndStartAppWithConfigFileName(String name) {
        app = Bootique
                .app("--config=classpath:io/bootique/jjwt/" + name)
                .autoLoadModules()
                .moduleProvider(new JjwtModuleProvider())
                .createRuntime();
    }

    @Test
    public void testKsWithoutKeyStoresConfig() {
        initAndStartAppWithConfigFileName("error-name.yml");
        try {
            app.getInstance(JwtParser.class);
            fail();
        } catch (RuntimeException e) {
           // e.printStackTrace();
            System.out.println(e.getCause().getMessage());
            assertTrue(e.getCause().getMessage().startsWith("keystore with name ["));
        }
    }
}
