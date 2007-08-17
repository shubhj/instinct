package com.googlecode.instinct.sandbox.composer;

import java.lang.reflect.Method;

public interface MethodImplementer {
    Object getObject();

    Method getMethod();

    Object invoke(Object... params);

    boolean hasMoreSpecificReturnTypeThan(MethodImplementer other);

    boolean hasMethodWithSameNameAndParametersAs(Method targetMethod);
}
