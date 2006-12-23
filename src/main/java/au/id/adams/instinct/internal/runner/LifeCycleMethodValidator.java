package au.id.adams.instinct.internal.runner;

import java.lang.reflect.Method;

public interface LifeCycleMethodValidator {
    void checkMethodHasNoParameters(Method method);
    <T> void checkContextConstructor(Class<T> cls);
}
