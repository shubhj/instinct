package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import com.googlecode.instinct.core.naming.NamingConvention;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class MarkedMethodLocatorImpl implements MarkedMethodLocator {
    private final AnnotatedMethodLocator annotatedMethodLocator = new AnnotatedMethodLocatorImpl();

    @Suggest("Write other locators, then include them here")
    public <A extends Annotation, T> Method[] locateAll(final Class<T> cls, final Class<A> annotationType, final NamingConvention namingConvention) {
        checkNotNull(cls, annotationType, namingConvention);
        return findMethodsByAnnotation(cls, annotationType);
    }

    private <A extends Annotation, T> Method[] findMethodsByAnnotation(final Class<T> cls, final Class<A> annotationType) {
        return annotatedMethodLocator.locate(cls, annotationType);
    }
}
