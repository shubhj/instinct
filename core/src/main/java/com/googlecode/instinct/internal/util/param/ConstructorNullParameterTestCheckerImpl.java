package com.googlecode.instinct.internal.util.param;

import com.googlecode.instinct.internal.util.NullMaster;
import com.googlecode.instinct.internal.util.NullMasterImpl;
import com.googlecode.instinct.internal.util.boost.ParameterCheckerTestUtil;
import com.googlecode.instinct.internal.util.boost.ParameterCheckerTestUtilImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
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
