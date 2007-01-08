package au.id.adams.instinct.internal.util;

import java.lang.reflect.Method;

public interface MethodInvoker {
    void invokeMethod(Object instance, Method method);
}
