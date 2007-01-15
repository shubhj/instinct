package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getField;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(AnnotationCheckerImpl.class, AnnotationChecker.class);
    }

    public void testClassIsAnnotated() {
        checkIsAnnotated(WithRuntimeAnnotations.class, BehaviourContext.class, true);
        checkIsAnnotated(WithoutRuntimeAnnotations.class, BehaviourContext.class, false);
    }

    public void testMethodIsAnnotated() {
        checkIsAnnotated(getMethod(WithRuntimeAnnotations.class, "toString"), Specification.class, true);
        checkIsAnnotated(getMethod(WithoutRuntimeAnnotations.class, "toString"), Specification.class, false);
    }

    public void testFieldIsAnnotated() {
        checkIsAnnotated(getField(WithRuntimeAnnotations.class, "string1"), Dummy.class, true);
        checkIsAnnotated(getField(WithoutRuntimeAnnotations.class, "string1"), Dummy.class, false);
    }

    private <A extends Annotation> void checkIsAnnotated(final AnnotatedElement annotatedElement, final Class<A> expectedAnnotation,
            final boolean expectingAnnotation) {
        final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
        assertEquals(expectingAnnotation, annotationChecker.isAnnotated(annotatedElement, expectedAnnotation));
    }
}
