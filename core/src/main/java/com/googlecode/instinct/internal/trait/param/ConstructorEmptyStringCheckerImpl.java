package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class ConstructorEmptyStringCheckerImpl implements ConstructorEmptyStringChecker {
    private final ParameterCheckerTestUtil parameterUtil;

    public ConstructorEmptyStringCheckerImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        parameterUtil = new ParameterCheckerTestUtilImpl(instanceProvider);
    }

    public <T> void checkPublicConstructorsRejectEmptyString(final Class<T> classToCheck) {
        checkNotNull(classToCheck);
        parameterUtil.checkConstructorsRejectEmptyString(classToCheck);
    }
}
