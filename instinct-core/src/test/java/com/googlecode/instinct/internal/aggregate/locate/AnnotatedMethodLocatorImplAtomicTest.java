package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Method;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class AnnotatedMethodLocatorImplAtomicTest extends InstinctTestCase {
    private static final Class<?> CLASS_WITH_NO_ANNOTATIONS = Object.class;
    private AnnotatedMethodLocator locator;

    public void testProperties() {
        checkClass(AnnotatedMethodLocatorImpl.class, AnnotatedMethodLocator.class);
    }

    public void testLocateOnAClassWithNoAnnotationsGiveNoMethod() {
        final Method[] methods = locator.locate(CLASS_WITH_NO_ANNOTATIONS, Suggest.class);
        assertEquals(0, methods.length);
    }

    @Override
    public void setUpSubject() {
        locator = new AnnotatedMethodLocatorImpl();
    }
}
