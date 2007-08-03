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
package com.googlecode.instinct.sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ComposingInvocationHandler<T> implements InvocationHandler {
    private Object[] implementers;

    public ComposingInvocationHandler(Object... implementers) {
        this.implementers = implementers;
    }

    public Object invoke(Object object, Method method, Object... params) {
        final MethodImplementer invokee = findImplementer(method);
        try {
            return invokee.invoke(params);
        } catch (InvocationTargetException ite) {
            throw new RuntimeException(ite.getCause());
        } catch (IllegalAccessException iae) {
            // TODO Can we do something better with exceptions declared by the interface
            throw new RuntimeException(iae);
        }
    }

    private MethodImplementer findImplementer(Method targetMethod) {
        final List<MethodImplementer> methodImplementers = new ArrayList<MethodImplementer>();
        for (Object implementer : implementers) {
            addMethodImplementations(implementer, methodImplementers);
        }
        return findImplementer(targetMethod, methodImplementers);
    }

    private void addMethodImplementations(Object implementer, List<MethodImplementer> methodImplementers) {
        for (Method method : implementer.getClass().getMethods()) {
            methodImplementers.add(new MethodImplementer(implementer, method));
        }
    }

    private MethodImplementer findImplementer(Method targetMethod, List<MethodImplementer> implementations) {
        MethodImplementer bestImplementation = null;
        for (MethodImplementer implementation : implementations) {
            bestImplementation = matchesAndHasMostSpecificReturnType(targetMethod, bestImplementation, implementation);
        }
        if (bestImplementation == null) {
            throw new NoSuchMethodError("No implementation found for: " + targetMethod);
        }
        return bestImplementation;
    }

    private MethodImplementer matchesAndHasMostSpecificReturnType(Method targetMethod, MethodImplementer champion,
            MethodImplementer contender) {
        if (contender.hasMethodWithSameNameAndParametersAs(targetMethod)) {
            return mostSpecificReturnType(champion, contender);
        } else {
            return champion;
        }
    }

    private MethodImplementer mostSpecificReturnType(MethodImplementer currentChampion, MethodImplementer contender) {
        if (currentChampion == null || contender.hasMoreSpecificReturnTypeThan(currentChampion)) {
            return contender;
        } else {
            return currentChampion;
        }
    }
}


