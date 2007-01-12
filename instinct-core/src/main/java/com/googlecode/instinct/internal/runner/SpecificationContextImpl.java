package com.googlecode.instinct.internal.runner;

import java.lang.reflect.Method;
import au.net.netstorm.boost.primordial.Primordial;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
final class SpecificationContextImpl extends Primordial implements SpecificationContext {
    private final Class<?> behaviourContextClass;
    private final Method[] beforeSpecificationMethods;
    private final Method[] afterSpecificationMethods;
    private final Method specificationMethod;

    SpecificationContextImpl(final Class<?> behaviourContextClass, final Method[] beforeSpecificationMethods,
            final Method[] afterSpecificationMethods, final Method specificationMethod) {
        checkNotNull(behaviourContextClass, beforeSpecificationMethods, afterSpecificationMethods, specificationMethod);
        this.behaviourContextClass = behaviourContextClass;
        this.beforeSpecificationMethods = beforeSpecificationMethods;
        this.afterSpecificationMethods = afterSpecificationMethods;
        this.specificationMethod = specificationMethod;
    }

    public Class<?> getBehaviourContextClass() {
        return behaviourContextClass;
    }

    public Method[] getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public Method[] getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }

    public Method getSpecificationMethod() {
        return specificationMethod;
    }
}
