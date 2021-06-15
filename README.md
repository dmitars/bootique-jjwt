<!--
   Licensed to ObjectStyle LLC under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ObjectStyle LLC licenses
   this file to you under the Apache License, Version 2.0 (the
   “License”); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.
  -->

# bootique-jjwt

Integration of JwtParser with Bootique.

## Usage

Create configuration with JwtParser properties and keystores metadata:
```yaml
parser:
    requireIssuer: 'https://example.org'
    id: my-id
    subject: my-subject
    audience: my-audience
    allowedClockSkewSeconds: 12
    keyStore: ks1
    keyAlias: mykey
    expiration: 2021-12-31
keystore:
    ks1:
      location: "classpath:io/bootique/jjwt/ks/ks1.jks"
      password: "passOfKs1"
      type: pkcs12
```
Inject ```JJwtFactory``` and start using Jjwt:
```java
@Inject
private JjwtFactory jjwtFactory;

public void doSomething() {
	JwtParser jwtParser = jjwtFactory.configJwtParser();
}
```
You can also configure ```JwtParser``` behaviour like:
```java
    Bootique
        .app("--config=classpath:io/bootique/jjwt/test.yml")
        .autoLoadModules()
        .moduleProvider(new KeystoreModuleProvider())
        .moduleProvider(new JjwtModuleProvider())
        .module(binder -> JjwtModule.extend(binder)
        .setSigningKeyResolver(new SigningKeyResolver() {
            @Override
            public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
                return null;
            }
            @Override
            public Key resolveSigningKey(JwsHeader jwsHeader, String s) {
                return null;
            }
        })
        .setDecoder(new Decoder<String, byte[]>() {
            @Override
            public byte[] decode(String s) throws DecodingException {
                return new byte[0];
            }
        }))
        .createRuntime();
```                    
            