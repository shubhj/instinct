package au.id.adams.instinct.internal.aggregate.locate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import au.id.adams.instinct.core.naming.NamingConvention;

public interface MethodLocator {
    <A extends Annotation, T> Method[] locateAll(Class<T> cls, Class<A> annotationType, NamingConvention namingConvention);
}
