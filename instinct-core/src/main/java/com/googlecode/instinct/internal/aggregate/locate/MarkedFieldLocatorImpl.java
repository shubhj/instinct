package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.googlecode.instinct.core.naming.NamingConvention;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class MarkedFieldLocatorImpl implements MarkedFieldLocator {
    private AnnotatedFieldLocator annotatedFieldLocator = new AnnotatedFieldLocatorImpl();

    @Suggest("Write other locators, then include them here")
    public <A extends Annotation, T> Field[] locateAll(final Class<T> cls, final Class<A> annotationType, final NamingConvention namingConvention) {
        checkNotNull(cls, annotationType, namingConvention);
        return findFieldsByAnnotation(cls, annotationType);
    }

    private <A extends Annotation, T> Field[] findFieldsByAnnotation(final Class<T> cls, final Class<A> annotationType) {
        return annotatedFieldLocator.locate(cls, annotationType);
    }
}
