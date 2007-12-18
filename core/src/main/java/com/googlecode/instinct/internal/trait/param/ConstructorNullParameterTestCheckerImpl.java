package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class ConstructorNullParameterTestCheckerImpl implements ConstructorNullParameterTestChecker {
    private final ParameterCheckerTestUtil parameterUtil;

    public ConstructorNullParameterTestCheckerImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        parameterUtil = new ParameterCheckerTestUtilImpl(instanceProvider);
    }

    public <T> void checkPublicConstructorsRejectNull(final Class<T> classToCheck) {
        checkNotNull(classToCheck);
        parameterUtil.checkConstructorsRejectsNull(classToCheck);
    }
}
