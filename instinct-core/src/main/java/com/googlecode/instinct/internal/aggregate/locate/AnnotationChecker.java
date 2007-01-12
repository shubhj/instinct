package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;

public interface AnnotationChecker {
    <T, A extends Annotation> boolean isAnnotated(Class<T> candidateClass, Class<A> annotationType);
}
