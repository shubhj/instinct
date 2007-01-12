package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class AnnotatedMethodLocatorImpl implements AnnotatedMethodLocator {
    @Suggest("Come back and drop in an annotation checker once written")
    public <A extends Annotation, T> Method[] locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        final List<Method> annotatedMethods = new ArrayList<Method>();
        for (final Method method : cls.getDeclaredMethods()) {
            final A annotation = method.getAnnotation(runtimeAnnotationType);
            if (annotation != null) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods.toArray(new Method[annotatedMethods.size()]);
    }
}
