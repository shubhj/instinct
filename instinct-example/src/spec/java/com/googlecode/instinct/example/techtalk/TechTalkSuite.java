package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.integrate.junit.BehaviourContextTestSuite;
import junit.framework.TestSuite;

public final class TechTalkSuite {
    private TechTalkSuite() {
        throw new UnsupportedOperationException();
    }

    public static TestSuite suite() {
        final TestSuite suite = new BehaviourContextTestSuite(ContextExample.class);
        suite.setName("Instinct JUnit Integration");
        return suite;
//        final TestSuite suite = new TestSuite();
//        suite.addTest(new BehaviourContextTestSuite(ContextExample.class));
//        return suite;
    }
}
