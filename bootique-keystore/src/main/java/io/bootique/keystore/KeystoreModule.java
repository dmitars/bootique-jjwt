package io.bootique.keystore;

import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Provides;
import io.bootique.keystore.model.ExtendedKeystore;

import javax.inject.Singleton;
import java.util.Map;

public class KeystoreModule extends ConfigModule {

    @Singleton
    @Provides
    KeystoreObjectsFactory provideKeystoreObjectsFactory(ConfigurationFactory configFactory) {
        return config(KeystoreObjectsFactory.class, configFactory);
    }

    @Provides
    Map<String, ExtendedKeystore> provideKeystores(KeystoreObjectsFactory keystoreObjectsFactory){
        return keystoreObjectsFactory.loadedKeystores();
    }
}
