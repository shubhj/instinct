package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.runner.ContextResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class ContextClassImpl implements ContextClass {
    private final ContextRunner contextRunner = new ContextRunnerImpl();
    private final Class<?> contextType;

    public <T> ContextClassImpl(final Class<T> contextType) {
        checkNotNull(contextType);
        this.contextType = contextType;
    }

    @Suggest("Can the spec listener go into the context? Perhaps every spec gets its own listener?")
    public ContextResult run(final ContextListener contextListener, final SpecificationListener specificationListener) {
        checkNotNull(contextListener);
        contextListener.onContext(this);
        return contextRunner.run(this, specificationListener);
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getType() {
        return (Class<T>) contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }
}
