package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.integrate.junit3.BehaviourContextTestSuite;
import junit.framework.TestSuite;

public final class TechTalkSuite {
    private TechTalkSuite() {
        throw new UnsupportedOperationException();
    }

    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Instinct Tech Talk");
        addContextToSuite(suite, AnEmptyStack.class);
        addContextToSuite(suite, AFullStack.class);
        addContextToSuite(suite, AnEmptyMagazineRack.class);
        addContextToSuite(suite, AGlossyMagazine.class);
        addContextToSuite(suite, MagazinePileContext.class);
        return suite;
    }

    private static <T> void addContextToSuite(final TestSuite suite, final Class<T> contextClass) {
        suite.addTest(newSuite(contextClass).testAt(0));
    }

    private static <T> BehaviourContextTestSuite newSuite(final Class<T> contextClass) {
        return new BehaviourContextTestSuite(contextClass);
    }
}