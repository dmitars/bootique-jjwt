package io.bootique.jjwt;

import io.bootique.BQRuntime;
import io.bootique.junit5.*;
import org.junit.jupiter.api.Test;

@BQTest
public class JjwtModuleProviderIT {
    @BQTestTool
    public BQTestFactory testFactory = new BQTestFactory();

    @Test
    public void testModuleDeclaresDependencies() {
        BQRuntime bqRuntime = testFactory.app().moduleProvider(new JjwtModuleProvider()).createRuntime();
        BQRuntimeChecker.testModulesLoaded(bqRuntime,
                JjwtModule.class
        );
    }
}
