package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.util.NullMaster;
import com.googlecode.instinct.internal.util.NullMasterImpl;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class ConstructorEmptyStringCheckerImpl implements ConstructorEmptyStringChecker {
    private final NullMaster nullMaster = new NullMasterImpl();
    private final ParameterCheckerTestUtil parameterUtil;

    public ConstructorEmptyStringCheckerImpl(final InstanceProvider instanceProvider) {
        nullMaster.check(instanceProvider);
        parameterUtil = new ParameterCheckerTestUtilImpl(instanceProvider);
    }

    public void checkPublicConstructorsRejectEmptyString(final Class classToCheck) {
        nullMaster.check(classToCheck);
        parameterUtil.checkConstructorsRejectEmptyString(classToCheck);
    }
}
