package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotationCheckerImpl implements AnnotationChecker {
    public <A extends Annotation> boolean isAnnotated(final AnnotatedElement annotatedElement, final Class<A> annotationType) {
        checkNotNull(annotatedElement, annotationType);
        return annotatedElement.isAnnotationPresent(annotationType);
    }
}
