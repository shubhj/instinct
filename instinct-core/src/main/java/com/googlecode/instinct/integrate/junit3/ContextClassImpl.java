package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ContextClassImpl implements ContextClass {
    private final ContextRunner contextRunner = new ContextRunnerImpl();
    private final Class<?> behaviourContextType;

    public <T> ContextClassImpl(final Class<T> behaviourContextType) {
        checkNotNull(behaviourContextType);
        this.behaviourContextType = behaviourContextType;
    }

    public ContextResult run(final ContextRunStrategy contextRunStrategy,
            final SpecificationRunStrategy specificationRunStrategy) {
        checkNotNull(contextRunStrategy);
        return contextRunner.run(this, contextRunStrategy, specificationRunStrategy);
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getType() {
        return (Class<T>) behaviourContextType;
    }

    public String getName() {
        return behaviourContextType.getSimpleName();
    }
}
