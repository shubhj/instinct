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

package com.googlecode.instinct.internal.util.lang;

import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import java.lang.reflect.Method;

public final class ReflectMethodMasterImpl implements ReflectMethodMaster {
    public <T> Method getMethod(final Class<T> cls, final MethodSpec method) {
        checkNotNull(cls, method);
        return doGetMethod(cls, method);
    }

    private <T> Method doGetMethod(final Class<T> cls, final MethodSpec targetMethod) {
        final Method[] methods = cls.getMethods();
        for (final Method sourceMethod : methods) {
            if (methodsMatch(sourceMethod, targetMethod)) {
                return sourceMethod;
            }
        }
        throw new NoSuchMethodError(targetMethod.toString());
    }

    private boolean methodsMatch(final Method sourceMethod, final MethodSpec targetMethod) {
        final String name = targetMethod.getName();
        if (!methodNamesMatch(sourceMethod, name)) {
            return false;
        }
        final Class<?>[] params = targetMethod.getParams();
        return paramsMatch(sourceMethod, params);
    }

    private boolean methodNamesMatch(final Method sourceMethod, final String targetMethodName) {
        final String methodName = sourceMethod.getName();
        return methodName.equals(targetMethodName);
    }

    private <T> boolean paramsMatch(final Method sourceMethod, final Class<?>[] parameterTypes) {
        final Class<?>[] params = sourceMethod.getParameterTypes();
        if (parameterTypes.length != params.length) {
            return false;
        }
        for (int i = 0; i < params.length; i++) {
            if (!paramMatches(params[i], parameterTypes[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean paramMatches(final Class<?> sourceParam, final Class<?> targetParam) {
        return sourceParam.isAssignableFrom(targetParam);
    }
}
