package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface AnnotationChecker {
    <T, A extends Annotation> boolean isAnnotated(Class<T> candidateClass, Class<A> annotationType);

    <A extends Annotation> boolean isAnnotated(Method method, Class<A> annotationType);
}
