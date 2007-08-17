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
package com.googlecode.instinct.sandbox.composer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ComposingInvocationHandler implements InvocationHandler {
    private Object[] implementers;

    public ComposingInvocationHandler(final Object... implementers) {
        this.implementers = implementers.clone();
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) {
        final MethodImplementer invokee = findImplementer(method);
        return invokee.invoke(args);
    }

    private MethodImplementer findImplementer(final Method targetMethod) {
        final List<MethodImplementorImpl> methodImplementers = new ArrayList<MethodImplementorImpl>();
        for (final Object implementer : implementers) {
            addMethodImplementations(implementer, methodImplementers);
        }
        return findImplementer(targetMethod, methodImplementers);
    }

    private void addMethodImplementations(final Object implementer, final List<MethodImplementorImpl> methodImplementers) {
        for (final Method method : implementer.getClass().getMethods()) {
            methodImplementers.add(new MethodImplementorImpl(implementer, method));
        }
    }

    private MethodImplementer findImplementer(final Method targetMethod, final List<MethodImplementorImpl> implementations) {
        MethodImplementer bestImplementation = null;
        for (final MethodImplementer implementation : implementations) {
            bestImplementation = matchesAndHasMostSpecificReturnType(targetMethod, bestImplementation, implementation);
        }
        if (bestImplementation == null) {
            throw new NoSuchMethodError("No implementation found for: " + targetMethod);
        }
        return bestImplementation;
    }

    private MethodImplementer matchesAndHasMostSpecificReturnType(final Method targetMethod, final MethodImplementer champion,
            final MethodImplementer contender) {
        if (contender.hasMethodWithSameNameAndParametersAs(targetMethod)) {
            return mostSpecificReturnType(champion, contender);
        } else {
            return champion;
        }
    }

    private MethodImplementer mostSpecificReturnType(final MethodImplementer currentChampion, final MethodImplementer contender) {
        if (currentChampion == null || contender.hasMoreSpecificReturnTypeThan(currentChampion)) {
            return contender;
        } else {
            return currentChampion;
        }
    }
}


