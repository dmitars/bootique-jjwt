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

package io.bootique.jjwt.model;

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

import java.util.Date;

@BQConfig
public class Parser {
    private String requireIssuer;
    private String keyStore;
    private String keyAlias;
    private String subject;
    private String audience;
    private Date expiration;
    private String id;
    private long allowedClockSkewSeconds;

    /**
     * Sets an requireIssuer property for the Parser object, which will be transferred to JwtParser.
     *
     * @param requireIssuer a requireIssuer property of JwtParser.
     */
    @BQConfigProperty("requireIssuer property of JwtParser")
    public void setRequireIssuer(String requireIssuer) {
        this.requireIssuer = requireIssuer;
    }

    /**
     * Sets keystore name from configs for the Parser object, which will be transferred to JwtParser.
     *
     * @param keyStore a name of keystore in configs, which JwtParser should use.
     */
    @BQConfigProperty("keystore name from configuration file")
    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Sets an alias of key in keystore, which JwtParser should use.
     *
     * @param keyAlias an alias of key for JwtParser.
     */
    @BQConfigProperty("alias of key in keystore")
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    /**
     * Sets an requireSubject property for the Parser object, which will be transferred to JwtParser.
     *
     * @param subject requireSubject property of JwtParser.
     */
    @BQConfigProperty("requireSubject property of JwtParser")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sets an requireAudience property for the Parser object, which will be transferred to JwtParser.
     *
     * @param audience requireAudience property of JwtParser.
     */
    @BQConfigProperty("requireExpiration property of JwtParser")
    public void setAudience(String audience) {
        this.audience = audience;
    }

    /**
     * Sets an requireExpiration property for the Parser object, which will be transferred to JwtParser.
     *
     * @param expiration requireExpiration property of JwtParser.
     */
    @BQConfigProperty("requireExpiration property of JwtParser")
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * Sets an requireId property for the Parser object, which will be transferred to JwtParser.
     *
     * @param id requireId property of JwtParser.
     */
    @BQConfigProperty("requireId property of JwtParser")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets an allowedClockSkewSeconds property for the Parser object, which will be transferred to JwtParser.
     *
     * @param allowedClockSkewSeconds allowedClockSkewSeconds property of JwtParser.
     */
    @BQConfigProperty("allowedClockSkewSeconds property of JwtParser. 0 by default")
    public void setAllowedClockSkewSeconds(long allowedClockSkewSeconds) {
        this.allowedClockSkewSeconds = allowedClockSkewSeconds;
    }

    public long getAllowedClockSkewSeconds() {
        return allowedClockSkewSeconds;
    }

    public String getId() {
        return id;
    }

    public String getAudience() {
        return audience;
    }

    public Date getExpiration() {
        return expiration;
    }

    public String getSubject() {
        return subject;
    }

    public String getRequireIssuer() {
        return requireIssuer;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public String getKeyAlias() {
        return keyAlias;
    }
}
