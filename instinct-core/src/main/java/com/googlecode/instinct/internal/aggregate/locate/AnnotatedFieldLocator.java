package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotatedFieldLocator {
    <A extends Annotation, T> Field[] locate(Class<T> cls, Class<A> runtimeAnnotationType);
}
