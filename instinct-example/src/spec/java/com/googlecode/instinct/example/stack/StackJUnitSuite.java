package com.googlecode.instinct.example.stack;

import com.googlecode.instinct.integrate.junit3.ContextTestSuite;
import junit.framework.TestSuite;

public final class StackJUnitSuite {
    private StackJUnitSuite() {
        throw new UnsupportedOperationException();
    }

    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Instinct JUnit Integration - Stack Example");
        addContextToSuite(suite, AnEmptyStack.class);
        addContextToSuite(suite, AFullStack.class);
        addContextToSuite(suite, AnEmptyMagazineRack.class);
        addContextToSuite(suite, AGlossyMagazine.class);
        addContextToSuite(suite, MagazinePileContext.class);
        return suite;
    }

    private static <T> void addContextToSuite(final TestSuite suite, final Class<T> contextClass) {
        suite.addTest(newSuite(contextClass));
    }

    private static <T> TestSuite newSuite(final Class<T> contextClass) {
        return new ContextTestSuite(contextClass);
    }
}
