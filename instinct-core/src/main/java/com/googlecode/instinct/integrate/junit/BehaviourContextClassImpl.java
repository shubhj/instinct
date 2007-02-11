package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextClassImpl implements BehaviourContextClass {
    private final BehaviourContextRunner contextRunner = new BehaviourContextRunner();
    private final Class<?> behaviourContext;

    public <T> BehaviourContextClassImpl(final Class<T> behaviourContext) {
        checkNotNull(behaviourContext);
        this.behaviourContext = behaviourContext;
    }

    public BehaviourContextResult run(final BehaviourContextRunStrategy behaviourContextRunStrategy,
            final SpecificationRunStrategy specificationRunStrategy) {
        checkNotNull(behaviourContextRunStrategy);
        return contextRunner.run(this, behaviourContextRunStrategy, specificationRunStrategy);
    }

    public String getName() {
        return "RemoveMe_" + behaviourContext.getSimpleName();
    }
}
