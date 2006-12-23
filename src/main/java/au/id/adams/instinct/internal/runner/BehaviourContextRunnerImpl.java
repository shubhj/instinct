package au.id.adams.instinct.internal.runner;

import java.lang.reflect.Method;
import au.id.adams.instinct.core.annotate.AfterTest;
import au.id.adams.instinct.core.annotate.BeforeTest;
import au.id.adams.instinct.core.annotate.Specification;
import au.id.adams.instinct.core.naming.AfterTestNamingConvention;
import au.id.adams.instinct.core.naming.BeforeTestNamingConvention;
import au.id.adams.instinct.core.naming.BehaviourContextNamingConvention;
import au.id.adams.instinct.internal.aggregate.locate.MethodLocator;
import au.id.adams.instinct.internal.aggregate.locate.MethodLocatorImpl;
import au.id.adams.instinct.internal.util.ConstructorInvokerImpl;
import au.id.adams.instinct.internal.util.MethodInvoker;
import au.id.adams.instinct.internal.util.MethodInvokerImpl;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;
import au.id.adams.instinct.internal.util.Suggest;
import au.id.adams.instinct.internal.util.ConstructorInvoker;
import au.id.adams.instinct.internal.mock.MockAutoWirerImpl;
import au.id.adams.instinct.internal.mock.MockAutoWirer;
import au.id.adams.instinct.internal.mock.MockVerifierImpl;
import au.id.adams.instinct.internal.mock.MockVerifier;

@Suggest("Class is too big, break up")
public final class BehaviourContextRunnerImpl implements BehaviourContextRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private MethodInvoker methodInvoker = new MethodInvokerImpl();
    private LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final MethodLocator methodLocator = new MethodLocatorImpl();
    private final MockAutoWirer mockAutoWirer = new MockAutoWirerImpl();
    private final MockVerifier mockVerifier = new MockVerifierImpl();

    public <T> void run(final Class<T> cls) {
        checkNotNull(cls);
        final Method[] specificationMethods = methodLocator.locateAll(cls, Specification.class, new BehaviourContextNamingConvention());
        final Method[] setUpMethods = methodLocator.locateAll(cls, BeforeTest.class, new BeforeTestNamingConvention());
        final Method[] tearDownMethods = methodLocator.locateAll(cls, AfterTest.class, new AfterTestNamingConvention());
        runSpecifications(cls, specificationMethods, setUpMethods, tearDownMethods);
    }

    private <T> void runSpecifications(final Class<T> cls, final Method[] specificationMethods, final Method[] setUpMethods,
            final Method[] tearDownMethods) {
        for (final Method specificationMethod : specificationMethods) {
            runSpecification(cls, specificationMethod, setUpMethods, tearDownMethods);
        }
    }

    @Suggest("Check JUNit 4 code for the way they are now doing finally in runBare()")
    private <T> void runSpecification(final Class<T> cls, final Method specificationMethod, final Method[] setUpMethods,
            final Method[] tearDownMethods) {
        final Object instance = invokeConstructor(cls);
        try {
            mockAutoWirer.wire(instance);
            runMethods(instance, setUpMethods);
            methodInvoker.invokeMethod(instance, specificationMethod);
            mockVerifier.verify(instance);
        } finally {
            runMethods(instance, tearDownMethods);
        }
    }

    private void runMethods(final Object instance, final Method[] methods) {
        for (final Method method : methods) {
            methodValidator.checkMethodHasNoParameters(method);
            methodInvoker.invokeMethod(instance, method);
        }
    }

    private <T> Object invokeConstructor(final Class<T> cls) {
        methodValidator.checkContextConstructor(cls);
        return constructorInvoker.invokeNullaryConstructor(cls);
    }
}
