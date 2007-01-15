package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.googlecode.instinct.core.naming.NamingConvention;

public interface MarkedFieldLocator {
    <A extends Annotation, T> Field[] locateAll(Class<T> cls, Class<A> annotationType, NamingConvention namingConvention);
}
