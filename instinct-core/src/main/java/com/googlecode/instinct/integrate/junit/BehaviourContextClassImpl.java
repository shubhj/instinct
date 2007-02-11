package com.googlecode.instinct.integrate.junit;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextClassImpl implements BehaviourContextClass {
    private final Class<?> behaviourContext;

    public <T> BehaviourContextClassImpl(final Class<T> behaviourContext) {
        checkNotNull(behaviourContext);
        this.behaviourContext = behaviourContext;
    }

    public String getName() {
        return "RemoveMe_" + behaviourContext.getSimpleName();
    }

    public void run(final BehaviourContextRunStrategy runStrategy) {
        new BehaviourContextRunner().run(this, runStrategy);
    }
}
