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

import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdgeImpl;
import com.googlecode.instinct.internal.util.lang.ReflectMasterImpl;
import com.googlecode.instinct.internal.util.lang.ReflectObjectMaster;
import java.lang.reflect.Constructor;

public final class ClassTestUtilImpl implements ClassTestUtil {
    private static final Object[] NO_PARAMETERS = {};
    private final ReflectObjectMaster reflector = new ReflectMasterImpl();
    private final ConstructorEdge edgeConstructor = new ConstructorEdgeImpl();

    // FIX SC509 Reorder parameters?
    public boolean isImplementationOf(final Interface targetInterface, final Class cls) {
        final Class type = targetInterface.getType();
        return isAssignable(type, cls);
    }

    public boolean isSubclassOf(final Class superClass, final Class subClass) {
        return isAssignable(superClass, subClass);
    }

    public boolean isSubInterfaceOf(final Interface superInterface, final Interface subInterface) {
        final Class superType = superInterface.getType();
        final Class subType = subInterface.getType();
        return isAssignable(superType, subType);
    }

    public Object newInstance(final Class type) {
        final Constructor constructor = reflector.getConstructor(type);
        constructor.setAccessible(true);
        return edgeConstructor.newInstance(constructor, NO_PARAMETERS);
    }

    private boolean isAssignable(final Class superType, final Class subType) {
        return superType.isAssignableFrom(subType);
    }
}
