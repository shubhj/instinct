package com.googlecode.instinct.internal.aggregate;

import java.lang.reflect.Method;
import com.googlecode.instinct.internal.aggregate.locate.AnnotatedMethodLocator;
import com.googlecode.instinct.internal.aggregate.locate.AnnotatedMethodLocatorImpl;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.test.InstinctTestCase;

public final class AnnotatedMethodLocatorSlowTest extends InstinctTestCase {
    private AnnotatedMethodLocator locator;

    public void testFindsCorrectNumberOfSpecifications() {
        final Method[] methods = locator.locate(TestContext1.class, Specification.class);
        assertEquals(4, methods.length);
    }

    @Override
    public void setUpSubject() {
        locator = new AnnotatedMethodLocatorImpl();
    }
}
