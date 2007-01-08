package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotatedMethodLocatorImpl implements AnnotatedMethodLocator {

    public <A extends Annotation, T> Method[] locate(final Class<T> cls, final Class<A> annotationType) {
        checkNotNull(cls, annotationType);
        final List<Method> annotatedMethods = new ArrayList<Method>();
        final Method[] allMethods = cls.getDeclaredMethods();
        for (final Method method : allMethods) {
            final A annotation = method.getAnnotation(annotationType);
            if (annotation != null) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods.toArray(new Method[annotatedMethods.size()]);
    }
}
