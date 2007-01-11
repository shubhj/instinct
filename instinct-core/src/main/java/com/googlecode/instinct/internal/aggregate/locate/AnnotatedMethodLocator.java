package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface AnnotatedMethodLocator {
    <A extends Annotation, T> Method[] locate(Class<T> cls, Class<A> runtimeAnnotationType);
}
