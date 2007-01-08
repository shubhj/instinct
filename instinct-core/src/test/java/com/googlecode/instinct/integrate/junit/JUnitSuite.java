package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.test.suite.AllTestSuite;
import junit.framework.Test;

public final class JUnitSuite {

    private JUnitSuite() {
        throw new UnsupportedOperationException();
    }

    public static Test suite() {
//        final TestSuite test = (TestSuite) new JUnitTestSuiteBuilderImpl(AllTestSuite.class).buildSuite("Behaviour Contexts");
//        return test.testAt(0);
        return new JUnitTestSuiteBuilderImpl(AllTestSuite.class).buildSuite("Behaviour Contexts");
    }
}
