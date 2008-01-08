package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class ConstructorNullParameterTestCheckerImpl implements ConstructorNullParameterTestChecker {
    private final ParameterTraitChecker parameterTraitChecker;

    public ConstructorNullParameterTestCheckerImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        parameterTraitChecker = new ParameterTraitCheckerImpl(instanceProvider);
    }

    public <T> void checkPublicConstructorsRejectNull(final Class<T> classToCheck) {
        checkNotNull(classToCheck);
        parameterTraitChecker.checkConstructorsRejectsNull(classToCheck);
    }
}
