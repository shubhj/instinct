package au.id.adams.instinct.internal.runner;

import java.lang.reflect.Method;
import au.id.adams.instinct.internal.mock.MockAutoWirer;
import au.id.adams.instinct.internal.mock.MockAutoWirerImpl;
import au.id.adams.instinct.internal.mock.MockVerifier;
import au.id.adams.instinct.internal.mock.MockVerifierImpl;
import au.id.adams.instinct.internal.util.ConstructorInvoker;
import au.id.adams.instinct.internal.util.ConstructorInvokerImpl;
import au.id.adams.instinct.internal.util.MethodInvoker;
import au.id.adams.instinct.internal.util.MethodInvokerImpl;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;
import au.id.adams.instinct.internal.util.Suggest;

final class SpecificationRunnerImpl implements SpecificationRunner {
    private final ConstructorInvoker constructorInvoker = new ConstructorInvokerImpl();
    private MethodInvoker methodInvoker = new MethodInvokerImpl();
    private LifeCycleMethodValidator methodValidator = new LifeCycleMethodValidatorImpl();
    private final MockAutoWirer mockAutoWirer = new MockAutoWirerImpl();
    private final MockVerifier mockVerifier = new MockVerifierImpl();

    @Suggest("Check JUNit 4 code for the way they are now doing finally in runBare()")
    public void run(final SpecificationContext context) {
        checkNotNull(context);
        final Object instance = invokeConstructor(context.getBehaviourContextClass());
        try {
            mockAutoWirer.wire(instance);
            runMethods(instance, context.getBeforeSpecificationMethods());
            methodInvoker.invokeMethod(instance, context.getSpecificationMethod());
            mockVerifier.verify(instance);
        } finally {
            runMethods(instance, context.getAfterSpecificationMethods());
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
