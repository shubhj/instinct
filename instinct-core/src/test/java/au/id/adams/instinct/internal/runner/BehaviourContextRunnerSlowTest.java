package au.id.adams.instinct.internal.runner;

import au.id.adams.instinct.core.BehaviourContextConfigurationException;
import static au.id.adams.instinct.test.AssertTestChecker.assertThrows;
import au.id.adams.instinct.test.InstinctTestCase;
import au.id.adams.instinct.internal.runner.BehaviourContextRunner;
import au.id.adams.instinct.internal.runner.BehaviourContextRunnerImpl;

public final class BehaviourContextRunnerSlowTest extends InstinctTestCase {
    private BehaviourContextRunner runner;

    public void testRunWithSuccess() {
        runContext(TestContextWithSetUpAndTearDown.class);
        runContext(TestContextWithSetUpAndTearDown.AnEmbeddedPublicContext.class);
        runContext(TestContextWithConstructors.APublicConstructor.class);
    }

    public void testInvalidConstructorsThrowConfigException() {
        checkInvalidConstructorsThrowConfigException(TestContextWithConstructors.AConstructorWithParameters.class);
        checkInvalidConstructorsThrowConfigException(TestContextWithConstructors.APrivateConstructor.class);
        checkInvalidConstructorsThrowConfigException(TestContextWithConstructors.APackageLocalConstructor.class);
        checkInvalidConstructorsThrowConfigException(TestContextWithConstructors.AProtectedConstructor.class);
    }

    private <T> void checkInvalidConstructorsThrowConfigException(final Class<T> cls) {
        assertThrows(BehaviourContextConfigurationException.class, new Runnable() {
            public void run() {
                runContext(cls);
            }
        });
    }

    private <T> void runContext(final Class<T> cls) {
        runner.run(cls);
    }

    @Override
    public void setUpSubjects() {
        runner = new BehaviourContextRunnerImpl();
    }
}
