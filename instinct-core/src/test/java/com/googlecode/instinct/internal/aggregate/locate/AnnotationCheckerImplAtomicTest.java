package com.googlecode.instinct.internal.aggregate.locate;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkProperties;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkProperties(AnnotationChecker.class, AnnotationCheckerImpl.class);
    }

    public void testIsAnnotated() {
        checkIsAnnotated(WithRuntimeAnnotations.class, true);
        checkIsAnnotated(WithoutRuntimeAnnotations.class, false);
    }

    private <T> void checkIsAnnotated(final Class<T> subject, final boolean expectingAnnotation) {
        final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
        assertEquals(expectingAnnotation, annotationChecker.isAnnotated(subject, BehaviourContext.class));
    }
}
