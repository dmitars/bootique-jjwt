package io.bootique.keystore;

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import io.bootique.keystore.model.ExtendedKeystore;
import io.bootique.keystore.model.KeyStoreMetadata;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@BQConfig
public class KeystoreObjectsFactory {
    private Map<String, KeyStoreMetadata> keystores;
    private Map<String, ExtendedKeystore> parsedKeystores;

    @BQConfigProperty("Map of keystores configs from config file; key - it's name, value - object with metadata")
    public void setKeystores(Map<String, KeyStoreMetadata> keystores) {
        this.keystores = keystores;
    }

    /**
     * Loads keystores from selected in config files and provides map, where key is name of keystore,
     * value - object with keystore and its password.
     */
    public Map<String, ExtendedKeystore> loadedKeystores() {
        Objects.requireNonNull(keystores, "Keystores configuration not found");
        return parsedKeystores != null ? parsedKeystores : (parsedKeystores = keystores.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toLoadedExtendedKeyStore())
                ));
    }
}
