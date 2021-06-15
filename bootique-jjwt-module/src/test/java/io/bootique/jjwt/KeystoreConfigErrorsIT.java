package io.bootique.jjwt;

import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.jjwt.model.Parser;
import io.bootique.junit5.BQTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testKsWithoutKeystoresConfig() {
        initAndStartAppWithConfigFileName("error-name.yml");
        JjwtObjectFactory factory = app.getInstance(JjwtObjectFactory.class);
        Parser parser = factory.getParser();
        assertThrows(RuntimeException.class, factory::configJwtParser,
                "keystore with name [" + parser.getKeyStore() + "] not found!");
    }
}
