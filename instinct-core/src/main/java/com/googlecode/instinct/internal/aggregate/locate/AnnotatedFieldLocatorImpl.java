package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotatedFieldLocatorImpl implements AnnotatedFieldLocator {
    private AnnotationChecker annotationChecker = new AnnotationCheckerImpl();

    public <A extends Annotation, T> Field[] locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        return findAnnotatedFields(cls.getDeclaredFields(), runtimeAnnotationType);
    }

    private <A extends Annotation> Field[] findAnnotatedFields(final Field[] declaredFields, final Class<A> runtimeAnnotationType) {
        final List<Field> annotatedFields = new ArrayList<Field>();
        for (final Field field : declaredFields) {
            if (annotationChecker.isAnnotated(field, runtimeAnnotationType)) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields.toArray(new Field[annotatedFields.size()]);
    }
}
