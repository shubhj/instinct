package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotationCheckerImpl implements AnnotationChecker {
    public <T, A extends Annotation> boolean isAnnotated(final Class<T> candidateClass, final Class<A> annotationType) {
        checkNotNull(candidateClass, annotationType);
        return candidateClass.isAnnotationPresent(annotationType);
    }
}
