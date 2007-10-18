package com.googlecode.instinct.example.junit;

import com.googlecode.instinct.example.stack.AGlossyMagazine;
import com.googlecode.instinct.example.stack.ANonEmptyStack;
import com.googlecode.instinct.example.stack.AnEmptyMagazineRack;
import com.googlecode.instinct.example.stack.AnEmptyStack;
import com.googlecode.instinct.integrate.junit3.ContextTestSuite;
import junit.framework.TestSuite;

public final class StackJUnitSuite {
    private StackJUnitSuite() {
        throw new UnsupportedOperationException();
    }

    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Instinct JUnit 3 Integration - Stack Example");
        addContextsToSuite(suite, AnEmptyStack.class, ANonEmptyStack.class, AnEmptyMagazineRack.class, AGlossyMagazine.class);
        return suite;
    }

    private static void addContextsToSuite(final TestSuite suite, final Class<?>... contextClasses) {
        for (final Class<?> contextClass : contextClasses) {
            suite.addTest(newSuite(contextClass));
        }
    }

    private static <T> TestSuite newSuite(final Class<T> contextType) {
        return new ContextTestSuite(contextType);
    }
}
