package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Method;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class AnnotatedMethodLocatorImplAtomicTest extends InstinctTestCase {
    private AnnotatedMethodLocator locator;

    public void testProperties() {
        checkClass(AnnotatedMethodLocatorImpl.class, AnnotatedMethodLocator.class);
    }

    public void testLocateOnAClassWithNoAnnotationsGiveNoMethod() {
        final Method[] methods = locator.locate(WithoutRuntimeAnnotatedMethods.class, Specification.class);
        assertEquals(0, methods.length);
    }

    public void testLocateOnAClassWithSeveralAnnotationsGiveSeveralMethod() {
        final Method[] methods = locator.locate(WithRuntimeAnnotatedMethods.class, Specification.class);
        assertEquals(2, methods.length);
    }

    @Override
    public void setUpSubject() {
        locator = new AnnotatedMethodLocatorImpl();
    }

    private static class WithoutRuntimeAnnotatedMethods {
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static class WithRuntimeAnnotatedMethods {
        @Override
        @Specification
        public String toString() {
            return super.toString();
        }

        @Override
        @Specification
        public boolean equals(final Object obj) {
            return super.equals(obj);
        }
    }
}
