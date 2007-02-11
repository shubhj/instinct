package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextClassImpl implements BehaviourContextClass {
    private final BehaviourContextRunner contextRunner = new BehaviourContextRunner();
    private final Class<?> behaviourContextType;

    public <T> BehaviourContextClassImpl(final Class<T> behaviourContextType) {
        checkNotNull(behaviourContextType);
        this.behaviourContextType = behaviourContextType;
    }

    public BehaviourContextResult run(final BehaviourContextRunStrategy behaviourContextRunStrategy,
            final SpecificationRunStrategy specificationRunStrategy) {
        checkNotNull(behaviourContextRunStrategy);
        return contextRunner.run(this, behaviourContextRunStrategy, specificationRunStrategy);
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getType() {
        return (Class<T>) behaviourContextType;
    }

    public String getName() {
        return "RemoveMe_" + behaviourContextType.getSimpleName();
    }
}
