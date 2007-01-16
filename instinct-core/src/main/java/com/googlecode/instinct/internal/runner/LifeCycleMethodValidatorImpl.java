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

package com.googlecode.instinct.internal.runner;

import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.EdgeException;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import com.googlecode.instinct.core.LifeCycleMethodConfigurationException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

final class LifeCycleMethodValidatorImpl implements LifeCycleMethodValidator {
    private final EdgeClass edgeClass = new DefaultEdgeClass();

    @Suggest("Consider adding parameter types to message for overloaded methods")
    public void checkMethodHasNoParameters(final Method method) {
        checkNotNull(method);
        if (method.getParameterTypes().length > 0) {
            final String methodDetails = method.getDeclaringClass().getSimpleName() + '.' + method.getName() + "(...)";
            final String message = "Unable to run context. Method '" + methodDetails + "' cannot have parameters";
            throw new LifeCycleMethodConfigurationException(message);
        }
    }

    public void checkMethodHasNoReturnType(final Method method) {
        checkNotNull(method);
        if (!method.getReturnType().equals(Void.TYPE)) {
            final String methodDetails = method.getDeclaringClass().getSimpleName() + '.' + method.getName() + "(...)";
            final String message = "Unable to run context. Method '" + methodDetails + "' must have void return type";
            throw new LifeCycleMethodConfigurationException(message);
        }
    }

    public <T> void checkContextConstructor(final Class<T> cls) {
        checkNotNull(cls);
        checkForPublicNullaryConstructor(cls);
    }

    private <T> void checkForPublicNullaryConstructor(final Class<T> cls) {
        try {
            edgeClass.getConstructor(cls, new Class<?>[]{});
        } catch (EdgeException e) {
            final String message = "Unable to run context. Context '" + cls.getSimpleName() + "' must have a public no-argument constructor";
            throw new BehaviourContextConfigurationException(message, e);
        }
    }
}
