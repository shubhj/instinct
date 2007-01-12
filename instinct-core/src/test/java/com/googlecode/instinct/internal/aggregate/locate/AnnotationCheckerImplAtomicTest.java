package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Method;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(AnnotationCheckerImpl.class, AnnotationChecker.class);
    }

    public void testClassIsAnnotated() {
        checkIsAnnotated(WithRuntimeAnnotations.class, true);
        checkIsAnnotated(WithoutRuntimeAnnotations.class, false);
    }

    public void testMethodIsAnnotated() {
        checkIsAnnotated(getMethod(WithRuntimeAnnotations.class, "toString"), true);
        checkIsAnnotated(getMethod(WithoutRuntimeAnnotations.class, "toString"), false);
    }

    private <T> void checkIsAnnotated(final Class<T> subject, final boolean expectingAnnotation) {
        final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
        assertEquals(expectingAnnotation, annotationChecker.isAnnotated(subject, BehaviourContext.class));
    }

    private void checkIsAnnotated(final Method method, final boolean expectingAnnotation) {
        final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
        assertEquals(expectingAnnotation, annotationChecker.isAnnotated(method, Specification.class));
    }
}
