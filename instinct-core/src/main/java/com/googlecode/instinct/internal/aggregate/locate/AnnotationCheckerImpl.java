package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;

public final class AnnotationCheckerImpl implements AnnotationChecker {
    public <T, A extends Annotation> boolean isAnnotated(final Class<T> candidateClass, final Class<A> annotationType) {
        return candidateClass.isAnnotationPresent(annotationType);
    }
}
