package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Method;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class AnnotatedMethodLocatorImplAtomicTest extends InstinctTestCase {
    private static final Class<?> CLASS_WITH_NO_ANNOTATED_METHODS = Object.class;
    private AnnotatedMethodLocator locator;

    public void testProperties() {
        checkClass(AnnotatedMethodLocatorImpl.class, AnnotatedMethodLocator.class);
    }

    public void testLocateOnAClassWithNoAnnotationsGiveNoMethod() {
        final Method[] methods = locator.locate(WithoutAnnotatedMethods.class, Override.class);
        assertEquals(0, methods.length);
    }

    public void testLocateOnAClassWithSeveralAnnotationsGiveSeveralMethod() {
        final Method[] methods = locator.locate(WithAnnotatedMethods.class, Override.class);
        assertEquals(0, methods.length);
    }

    @Override
    public void setUpSubject() {
        locator = new AnnotatedMethodLocatorImpl();
    }

    @SuppressWarnings({"EmptyClass"})
    private static class WithoutAnnotatedMethods {
    }

    private static class WithAnnotatedMethods {

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public boolean equals(final Object obj) {
            return super.equals(obj);
        }
    }
}
