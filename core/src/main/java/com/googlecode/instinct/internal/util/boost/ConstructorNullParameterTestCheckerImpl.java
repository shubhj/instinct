package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.util.NullMaster;
import com.googlecode.instinct.internal.util.NullMasterImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class ConstructorNullParameterTestCheckerImpl implements ConstructorNullParameterTestChecker {
    private final NullMaster nullMaster = new NullMasterImpl();
    private final ParameterCheckerTestUtil parameterUtil;

    public ConstructorNullParameterTestCheckerImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        parameterUtil = new ParameterCheckerTestUtilImpl(instanceProvider);
    }

    public void checkPublicConstructorsRejectNull(final Class classToCheck) {
        checkNotNull(classToCheck);
        parameterUtil.checkConstructorsRejectsNull(classToCheck);
    }
}
