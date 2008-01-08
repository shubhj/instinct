package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class ConstructorEmptyStringCheckerImpl implements ConstructorEmptyStringChecker {
    private final ParameterTraitChecker parameterTraitChecker;

    public ConstructorEmptyStringCheckerImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        parameterTraitChecker = new ParameterTraitCheckerImpl(instanceProvider);
    }

    public <T> void checkPublicConstructorsRejectEmptyString(final Class<T> classToCheck) {
        checkNotNull(classToCheck);
        parameterTraitChecker.checkConstructorsRejectEmptyString(classToCheck);
    }
}
