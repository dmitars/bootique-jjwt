package io.bootique.keystore;

import io.bootique.BQModuleMetadata;
import io.bootique.BQModuleProvider;
import io.bootique.di.BQModule;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

public class KeystoreModuleProvider implements BQModuleProvider {
    @Override
    public BQModule module() {
        return new KeystoreModule();
    }

    @Override
    public Map<String, Type> configs() {
        return Collections.singletonMap("jjwt", KeystoreObjectsFactory.class);
    }

    @Override
    public BQModuleMetadata.Builder moduleBuilder() {
        return BQModuleProvider.super
                .moduleBuilder()
                .description("Provides ability to config keystores and use them in another bootique modules.");
    }
}
