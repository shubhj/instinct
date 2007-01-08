package com.googlecode.instinct.internal.runner;

import java.lang.reflect.Method;
import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.EdgeException;

@Suggest("Should we check for void?")
final class LifeCycleMethodValidatorImpl implements LifeCycleMethodValidator {
    private final EdgeClass edgeClass = new DefaultEdgeClass();

    @Suggest("Consider adding parameter types to message for overloaded methods")
    public void checkMethodHasNoParameters(final Method method) {
        checkNotNull(method);
        if (method.getParameterTypes().length > 0) {
            final String methodDetails = method.getDeclaringClass().getSimpleName() + '.' + method.getName() + "(...)";
            final String message = "Unable to run context. Specification '" + methodDetails + "' cannot have parameters";
            throw new BehaviourContextConfigurationException(message);
        }
    }

    public <T> void checkContextConstructor(final Class<T> cls) {
        checkNotNull(cls);
        checkForPublicNullaryConstructor(cls);
    }

    private <T> void checkForPublicNullaryConstructor(final Class<T> cls) {
        try {
            edgeClass.getConstructor(cls, new Class<?>[]{});
        } catch (EdgeException e) {
            final String message = "Unable to run context. Context '" + cls.getSimpleName() + "' must have a public no-argument constructor";
            throw new BehaviourContextConfigurationException(message, e);
        }
    }
}
