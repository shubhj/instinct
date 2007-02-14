package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.integrate.junit3.BehaviourContextTestSuite;
import junit.framework.TestSuite;

public final class TechTalkSuite {
    private TechTalkSuite() {
        throw new UnsupportedOperationException();
    }

    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Instinct Tech Talk");
        suite.addTest(new BehaviourContextTestSuite(AnEmptyStack.class).testAt(0));
        suite.addTest(new BehaviourContextTestSuite(AFullStack.class).testAt(0));
        suite.addTest(new BehaviourContextTestSuite(AnEmptyMagazineRack.class).testAt(0));
        suite.addTest(new BehaviourContextTestSuite(AGlossyMagazine.class).testAt(0));
        return suite;
    }
}
