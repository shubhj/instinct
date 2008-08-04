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

import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.ContextConfigurationException;
import com.googlecode.instinct.marker.LifeCycleMethodConfigurationException;
import fj.data.Option;
import static fj.data.Option.none;
import static fj.data.Option.some;

public final class LifeCycleMethodValidatorImpl implements LifeCycleMethodValidator {
    private final ClassEdge edgeClass = new ClassEdgeImpl();

    @Suggest("Consider adding parameter types to message for overloaded methods")
    public Option<Throwable> checkMethodHasNoParameters(final LifecycleMethod method) {
        checkNotNull(method);
        if (method.getMethod().getParameterTypes().length > 0) {
            final String methodDetails = method.getContextClass().getSimpleName() + '.' + method.getName() + "(...)";
            final String message = "Unable to run context. Specifaction method '" + methodDetails + "' cannot have parameters";
            return some((Throwable) new LifeCycleMethodConfigurationException(message));
        } else {
            return none();
        }
    }

    public <T> Option<Throwable> checkContextConstructor(final Class<T> cls) {
        checkNotNull(cls);
        return checkClassContainsNullaryConstructor(cls);
    }

    private <T> Option<Throwable> checkClassContainsNullaryConstructor(final Class<T> cls) {
        try {
            edgeClass.getDeclaredConstructor(cls);
            return none();
        } catch (EdgeException e) {
            final String message = "Unable to run context. Context '" + cls.getSimpleName() + "' must have a no-argument constructor";
            return some((Throwable) new ContextConfigurationException(message, e));
        }
    }
}
