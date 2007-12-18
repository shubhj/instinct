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

package com.googlecode.instinct.internal.trait.param;

import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdgeImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import com.googlecode.instinct.internal.trait.modifier.ModifierTestUtil;
import com.googlecode.instinct.internal.trait.modifier.ModifierTestUtilImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.boost.AssertException;
import com.googlecode.instinct.internal.util.boost.AssertExceptionImpl;
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
    private final InstanceProvider instanceProvider;

    public ParameterCheckerTestUtilImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        this.instanceProvider = instanceProvider;
    }

    @SuppressWarnings({"unchecked"})
    public <T> void checkConstructorsRejectsNull(final Class<T> classToCheck) {
        checkNotNull(classToCheck);
        final Constructor<T>[] constructors = classToCheck.getConstructors();
        for (final Constructor<?> constructor : constructors) {
            checkConstructorRejectsNull(constructor);
        }
    }

    @SuppressWarnings({"unchecked"})
    public <T> void checkConstructorsRejectEmptyString(final Class<T> classToCheck) {
        checkNotNull(classToCheck);
        final Constructor<T>[] constructors = classToCheck.getConstructors();
        for (final Constructor<T> constructor : constructors) {
            checkConstructorRejectsEmptyString(constructor);
        }
    }

    public void checkMethodsRejectsNull(final Object instance) {
        checkNotNull(instance);
        final List<Method> methods = getPublicMethods(instance.getClass());
        for (final Object method : methods) {
            checkMethodRejectsNull(instance, (Method) method);
        }
    }

    public void checkMethodsRejectEmptyString(final Object instance) {
        checkNotNull(instance);
        final List<Method> methods = getPublicMethods(instance.getClass());
        for (final Object method : methods) {
            checkMethodsRejectsEmptyString(instance, (Method) method);
        }
    }

    private void checkMethodsRejectsEmptyString(final Object instance, final Method method) {
        final Class<?>[] paramTypes = method.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            emptyStringCheckParameters(instance, method, paramTypes, paramToCheck);
        }
    }

    private void checkMethodRejectsNull(final Object instance, final Method method) {
        final Class<?>[] paramTypes = method.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            // Note. We don't need to check primitives for null.
            if (!paramTypes[paramToCheck].isPrimitive()) {
                checkParameter(instance, method, paramTypes, paramToCheck, null, "null");
            }
        }
    }

    private void emptyStringCheckParameters(final Object instance, final Method method, final Class<?>[] paramTypes, final int paramToCheck) {
        if (isAString(paramTypes[paramToCheck])) {
            checkParameter(instance, method, paramTypes, paramToCheck, "", "empty string");
            checkParameter(instance, method, paramTypes, paramToCheck, " ", "empty string");
        }
    }

    private <T> List<Method> getPublicMethods(final Class<T> cls) {
        final List<Method> publicMethods = new ArrayList<Method>();
        final Method[] methods = cls.getDeclaredMethods();
        for (final Method method : methods) {
            if (modifierUtil.isPublic(method)) {
                publicMethods.add(method);
            }
        }
        return publicMethods;
    }

    private <T> void checkConstructorRejectsNull(final Constructor<T> constructor) {
        final Class<?>[] paramTypes = constructor.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            // Note. We don't need to check primitives for null.
            if (!paramTypes[paramToCheck].isPrimitive()) {
                checkParameter(constructor, paramTypes, paramToCheck, null, "null");
            }
        }
    }

    private <T> void checkConstructorRejectsEmptyString(final Constructor<T> constructor) {
        final Class<?>[] paramTypes = constructor.getParameterTypes();
        for (int paramToCheck = 0; paramToCheck < paramTypes.length; paramToCheck++) {
            emptyStringCheckParameters(constructor, paramTypes, paramToCheck);
        }
    }

    private <T> void emptyStringCheckParameters(final Constructor<T> constructor, final Class<?>[] paramTypes, final int paramToCheck) {
        if (isAString(paramTypes[paramToCheck])) {
            checkParameter(constructor, paramTypes, paramToCheck, "", "empty string");
            checkParameter(constructor, paramTypes, paramToCheck, " ", "empty string");
        }
    }

    private <T> void checkParameter(final Constructor<T> constructor, final Class<?>[] paramTypes, final int paramToCheck, final Object badParamValue,
            final String typeThatShouldHaveBeedRejected) {
        final Object[] parameterValues = createBadParamValues(instanceProvider, paramTypes, paramToCheck, badParamValue);
        checkFailsWithInvalidValues(constructor, paramToCheck, parameterValues, typeThatShouldHaveBeedRejected);
    }

    private void checkParameter(final Object instance, final Method method, final Class<?>[] paramTypes, final int paramToCheck,
            final Object badParamValue, final String typeThatShouldHaveBeedRejected) {
        final Object[] parameterValues = createBadParamValues(instanceProvider, paramTypes, paramToCheck, badParamValue);
        checkFailsWithInvalidValues(instance, method, paramToCheck, parameterValues, typeThatShouldHaveBeedRejected);
    }

    private <T> Object[] createBadParamValues(final InstanceProvider instanceProvider, final Class<T>[] paramTypes, final int indexOfParamToMakeBad,
            final Object badValue) {
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

    private <T> void checkFailsWithInvalidValues(final Constructor<T> constructor, final int currentParameter, final Object[] paramValues,
            final String badParamTypeName) {
        try {
            invoke(constructor, paramValues);
            failConstructor(currentParameter, constructor, badParamTypeName);
        } catch (Exception e) {
            assertException.checkExceptionClass(IllegalArgumentException.class, e);
        }
    }

    private void checkFailsWithInvalidValues(final Object instance, final Method method, final int currentParameter, final Object[] parameterValues,
            final String typeThatShouldHaveBeedRejected) {
        try {
            invoke(instance, method, parameterValues);
            failMethod(currentParameter, method, typeThatShouldHaveBeedRejected);
        } catch (Exception e) {
            assertException.checkExceptionClass(IllegalArgumentException.class, e);
        }
    }

    private <T> boolean isAString(final Class<T> type) {
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

    private <T> void invoke(final Constructor<T> constructor, final Object[] paramValues) {
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

    private <T> void failConstructor(final int currentParameter, final Constructor<T> constructor, final String badParamTypeName) {
        final String paramTypeClassName = constructor.getParameterTypes()[currentParameter].getSimpleName();
        final String methodName = constructor.getDeclaringClass().getSimpleName();
        fail(currentParameter, methodName, paramTypeClassName, badParamTypeName);
    }

    private void fail(final int currentParameter, final String methodName, final String paramTypeClassName,
            final String typeThatShouldHaveBeedRejected) {
        final String message = "Argument " + (currentParameter + 1) + " of " + methodName + "(..." + paramTypeClassName + "...) must be " +
                typeThatShouldHaveBeedRejected + " checked";
        Assert.fail(message);
    }

    private void setBadParam(final Object[] paramValues, final Class<?>[] paramTypes, final int indexOfParamToMakeBad, final Object badValue) {
        if (badValue == null) {
            paramValues[indexOfParamToMakeBad] = badValue;
        } else if (badValue.getClass().isAssignableFrom(paramTypes[indexOfParamToMakeBad])) {
            paramValues[indexOfParamToMakeBad] = badValue;
        } else {
            throw new RuntimeException("Expected value '" + badValue + "' to be of type " + paramTypes[indexOfParamToMakeBad].getSimpleName());
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
