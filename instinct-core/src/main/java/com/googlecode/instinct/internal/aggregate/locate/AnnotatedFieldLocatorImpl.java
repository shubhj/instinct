package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotatedFieldLocatorImpl implements AnnotatedFieldLocator {
    public <A extends Annotation, T> Field[] locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        return new Field[]{};
    }
}
