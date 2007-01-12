package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotatedMethodLocatorImpl implements AnnotatedMethodLocator {
    private final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();

    public <A extends Annotation, T> Method[] locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        final List<Method> annotatedMethods = new ArrayList<Method>();
        for (final Method method : cls.getDeclaredMethods()) {
            if (annotationChecker.isAnnotated(method, runtimeAnnotationType)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods.toArray(new Method[annotatedMethods.size()]);
    }
}
