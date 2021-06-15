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

    @BQConfigProperty
    public void setRequireIssuer(String requireIssuer) {
        this.requireIssuer = requireIssuer;
    }

    @BQConfigProperty
    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    @BQConfigProperty
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    @BQConfigProperty
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @BQConfigProperty
    public void setAudience(String audience) {
        this.audience = audience;
    }

    @BQConfigProperty
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    @BQConfigProperty
    public void setId(String id) {
        this.id = id;
    }

    @BQConfigProperty
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
