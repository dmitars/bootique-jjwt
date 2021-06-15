package io.bootique.jjwt;

import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.jjwt.model.KeyStoreMetadata;
import io.bootique.jjwt.model.Parser;
import io.bootique.junit5.BQApp;
import io.bootique.junit5.BQTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@BQTest
public class ConfigurationIT {
    @BQApp(skipRun = true)
    static final BQRuntime app = Bootique
            .app("--config=classpath:io/bootique/jjwt/test.yml")
            .autoLoadModules()
            .moduleProvider(new JjwtModuleProvider())
            .createRuntime();

    @Test
    public void testParserConfiguration() {
        Parser parser = app.getInstance(JjwtObjectFactory.class).getParser();
        assertNotNull(parser);
        assertEquals("mykey", parser.getKeyAlias());
        assertEquals("ks1", parser.getKeyStore());
        assertEquals("https://example.org", parser.getRequireIssuer());
    }

    @Test
    public void testKeystoresConfiguration() {
        Map<String, KeyStoreMetadata> keyStoreMap = app.getInstance(JjwtObjectFactory.class).getKeystores();
        assertNotNull(keyStoreMap);

        assertEquals(keyStoreMap.size(), 2);

        assertTrue(keyStoreMap.containsKey("ks1"));
        KeyStoreMetadata ks1 = keyStoreMap.get("ks1");
        assertEquals("classpath:ks1.jks", ks1.getLocation());
        assertEquals("passOfKs1", ks1.getPassword());

        assertTrue(keyStoreMap.containsKey("ks2"));
        KeyStoreMetadata ks2 = keyStoreMap.get("ks2");
        assertEquals("classpath:ks2.jks", ks2.getLocation());
        assertEquals("passOfKs2", ks2.getPassword());
    }
}
