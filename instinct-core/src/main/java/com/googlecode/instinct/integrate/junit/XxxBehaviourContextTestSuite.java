package com.googlecode.instinct.integrate.junit;

import com.googlecode.instinct.core.marker.BehaviourContext;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.TestSuite;

public final class XxxBehaviourContextTestSuite extends TestSuite {
    @Suggest("Do we need to invoke run here?")
    public XxxBehaviourContextTestSuite(final Class<? extends BehaviourContext> behaviourContextClass) {
        // run the tests

    }
}
