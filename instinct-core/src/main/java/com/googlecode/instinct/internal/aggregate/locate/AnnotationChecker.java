package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface AnnotationChecker {
    <A extends Annotation> boolean isAnnotated(AnnotatedElement annotatedElement, Class<A> annotationType);
}
