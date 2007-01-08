package au.id.adams.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import au.id.adams.instinct.core.naming.NamingConvention;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;
import au.id.adams.instinct.internal.util.Suggest;

public final class MethodLocatorImpl implements MethodLocator {
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
