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

package com.googlecode.instinct.internal.core;

import au.net.netstorm.boost.primordial.Primordial;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class LifecycleMethodImpl extends Primordial implements LifecycleMethod {
    private final Method method;

    public LifecycleMethodImpl(final Method method) {
        checkNotNull(method);
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }

    public Annotation[] getAnnotations() {
        return method.getAnnotations();
    }

    public Annotation[][] getParameterAnnotations() {
        return method.getParameterAnnotations();
    }
}
