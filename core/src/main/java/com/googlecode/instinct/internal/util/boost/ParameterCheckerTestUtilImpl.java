/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdgeImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import com.googlecode.instinct.internal.util.NullMaster;
import com.googlecode.instinct.internal.util.NullMasterImpl;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

public final class ParameterCheckerTestUtilImpl implements ParameterCheckerTestUtil {
    private final ConstructorEdge edgeConstructor = new ConstructorEdgeImpl();
    private final AssertException assertException = new AssertExceptionImpl();
    private final ModifierTestUtil modifierUtil = new ModifierTestUtilImpl();
    private final NullMaster nullMaster = new NullMasterImpl();
    private final InstanceProvider instanceProvider;

    public ParameterCheckerTestUtilImpl(final InstanceProvider instanceProvider) {
        nullMaster.check(instanceProvider);
        this.instanceProvider = instanceProvider;
    }

    public void checkConstructorsRejectsNull(final Class classToCheck) {
        nullMaster.check(classToCheck);
        final Constructor[] constructors = classToCheck.getConstructors();
        for (final Constructor constructor : constructors) {
            checkConstructorRejectsNull(constructor);
        }
    }

    public void checkConstructorsRejectEmptyString(final Class classToCheck) {
        nullMaster.check(classToCheck);
        final Constructor[] constructors = classToCheck.getConstructors();
        for (final Constructor constructor : constructors) {
            checkConstructorRejectsEmptyString(constructor);
        }
    }

    public void checkMethodsRejectsNull(final Object instance) {
        nullMaster.check(instance);
        final List methods = getPublicMethods(instance.getClass());
        for (final Object method : methods) {
            checkMethodRejectsNull(instance, (Method) method);
        }
    }

    public void checkMethodsRejectEmptyString(final Object instance) {
        nullMaster.check(instance);
        final List methods = getPublicMethods(instance.getClass());
        for (final Object method : methods) {
            checkMethodsRejectsEmptyString(instance, (Method) method);
        }
    }

    private void checkMethodsRejectsEmptyString(final Object instance, final Method method) {
        final Class[] paramTypes = method.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            emptyStringCheckParameters(instance, method, paramTypes, paramToCheck);
        }
    }

    private void checkMethodRejectsNull(final Object instance, final Method method) {
        final Class[] paramTypes = method.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            // Note. We don't need to check primitives for null.
            if (!paramTypes[paramToCheck].isPrimitive()) {
                checkParameter(instance, method, paramTypes, paramToCheck, null, "null");
            }
        }
    }

    private void emptyStringCheckParameters(final Object instance, final Method method, final Class[] paramTypes, final int paramToCheck) {
        if (isAString(paramTypes[paramToCheck])) {
            checkParameter(instance, method, paramTypes, paramToCheck, "", "empty string");
            checkParameter(instance, method, paramTypes, paramToCheck, " ", "empty string");
        }
    }

    private List getPublicMethods(final Class cls) {
        final List publicMethods = new ArrayList();
        final Method[] methods = cls.getDeclaredMethods();
        for (final Method method : methods) {
            if (modifierUtil.isPublic(method)) {
                publicMethods.add(method);
            }
        }
        return publicMethods;
    }

    private void checkConstructorRejectsNull(final Constructor constructor) {
        final Class[] paramTypes = constructor.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            // Note. We don't need to check primitives for null.
            if (!paramTypes[paramToCheck].isPrimitive()) {
                checkParameter(constructor, paramTypes, paramToCheck, null, "null");
            }
        }
    }

    private void checkConstructorRejectsEmptyString(final Constructor constructor) {
        final Class[] paramTypes = constructor.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            emptyStringCheckParameters(constructor, paramTypes, paramToCheck);
        }
    }

    private void emptyStringCheckParameters(final Constructor constructor, final Class[] paramTypes, final int paramToCheck) {
        if (isAString(paramTypes[paramToCheck])) {
            checkParameter(constructor, paramTypes, paramToCheck, "", "empty string");
            checkParameter(constructor, paramTypes, paramToCheck, " ", "empty string");
        }
    }

    private void checkParameter(final Constructor constructor, final Class[] paramTypes, final int paramToCheck,
            final Object badParamValue, final String typeThatShouldHaveBeedRejected) {
        final Object[] parameterValues = createBadParamValues(instanceProvider, paramTypes, paramToCheck, badParamValue);
        checkFailsWithInvalidValues(constructor, paramToCheck, parameterValues, typeThatShouldHaveBeedRejected);
    }

    private void checkParameter(final Object instance, final Method method, final Class[] paramTypes, final int paramToCheck,
            final Object badParamValue, final String typeThatShouldHaveBeedRejected) {
        final Object[] parameterValues = createBadParamValues(instanceProvider, paramTypes, paramToCheck, badParamValue);
        checkFailsWithInvalidValues(instance, method, paramToCheck, parameterValues, typeThatShouldHaveBeedRejected);
    }

    private Object[] createBadParamValues(final InstanceProvider instanceProvider, final Class[] paramTypes,
            final int indexOfParamToMakeBad, final Object badValue) {
        final Object[] paramValues = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramValues[i] = instanceProvider.newInstance(paramTypes[i]);
        }
        setBadParam(paramValues, paramTypes, indexOfParamToMakeBad, badValue);
        return paramValues;
    }

    private void handleException(final Throwable e) {
        final Throwable cause = e.getCause();
        if (cause instanceof InvocationTargetException) {
            final Throwable realCause = cause.getCause();
            rethrowException(realCause);
        }
    }

    private void checkFailsWithInvalidValues(final Constructor constructor, final int currentParameter, final Object[] paramValues,
            final String badParamTypeName) {
        try {
            invoke(constructor, paramValues);
            failConstructor(currentParameter, constructor, badParamTypeName);
        } catch (Exception e) {
            assertException.checkExceptionClass(IllegalArgumentException.class, e);
        }
    }

    private void checkFailsWithInvalidValues(final Object instance, final Method method, final int currentParameter,
            final Object[] parameterValues, final String typeThatShouldHaveBeedRejected) {
        try {
            invoke(instance, method, parameterValues);
            failMethod(currentParameter, method, typeThatShouldHaveBeedRejected);
        } catch (Exception e) {
            assertException.checkExceptionClass(IllegalArgumentException.class, e);
        }
    }

    private boolean isAString(final Class type) {
        return String.class.isAssignableFrom(type);
    }

    private void invoke(final Object instance, final Method method, final Object[] paramValues) {
        method.setAccessible(true);
        try {
            new MethodEdgeImpl(method).invoke(method, instance, paramValues);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    private void invoke(final Constructor constructor, final Object[] paramValues) {
        constructor.setAccessible(true);
        try {
            edgeConstructor.newInstance(constructor, paramValues);
        } catch (EdgeException e) {
            handleException(e);
        }
    }

    private void failMethod(final int currentParameter, final Method method, final String typeThatShouldHaveBeedRejected) {
        final String paramTypeClassName = method.getParameterTypes()[currentParameter].getSimpleName();
        final String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        fail(currentParameter, methodName, paramTypeClassName, typeThatShouldHaveBeedRejected);
    }

    private void failConstructor(final int currentParameter, final Constructor constructor, final String badParamTypeName) {
        final String paramTypeClassName = constructor.getParameterTypes()[currentParameter].getSimpleName();
        final String methodName = constructor.getDeclaringClass().getSimpleName();
        fail(currentParameter, methodName, paramTypeClassName, badParamTypeName);
    }

    private void fail(final int currentParameter, final String methodName, final String paramTypeClassName,
            final String typeThatShouldHaveBeedRejected) {
        final String message = "Argument " + (currentParameter + 1) + " of " + methodName + "(..." + paramTypeClassName
                + "...) must be " + typeThatShouldHaveBeedRejected + " checked";
        Assert.fail(message);
    }

    private void setBadParam(final Object[] paramValues, final Class[] paramTypes, final int indexOfParamToMakeBad, final Object badValue) {
        if (badValue == null) {
            paramValues[indexOfParamToMakeBad] = badValue;
        } else if (badValue.getClass().isAssignableFrom(paramTypes[indexOfParamToMakeBad])) {
            paramValues[indexOfParamToMakeBad] = badValue;
        } else {
            throw new RuntimeException("Expected value '" + badValue + "' to be of type " +
                    paramTypes[indexOfParamToMakeBad].getSimpleName());
        }
    }

    private void rethrowException(final Throwable realCause) {
        if (realCause instanceof IllegalArgumentException) {
            throw (IllegalArgumentException) realCause;
        } else {
            throw new RuntimeException(realCause);
        }
    }
}
