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

package com.googlecode.instinct.internal.reflect;

import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ConstructorEdgeImpl;
import java.lang.reflect.Constructor;
import static java.lang.reflect.Modifier.isAbstract;

public final class ConstructorInvokerImpl implements ConstructorInvoker {
    private final ClassEdge classEdge = new ClassEdgeImpl();
    private final ConstructorEdge edgeConstructor = new ConstructorEdgeImpl();

    public <T> T invokeNullaryConstructor(final Class<T> cls) {
        checkClassIsNotAbstract(cls);
        final Constructor<T> constructor = getNullaryConstructor(cls);
        return edgeConstructor.newInstance(constructor);
    }

    private <T> Constructor<T> getNullaryConstructor(final Class<T> cls) {
        try {
            final Constructor<T> constructor = classEdge.getDeclaredConstructor(cls);
            constructor.setAccessible(true);
            return constructor;
        } catch (EdgeException e) {
            throw new RuntimeException("Unable to instantiate " + cls.getName() + " as it does not have a nullary (no-args) constructor", e);
        }
    }

    private <T> void checkClassIsNotAbstract(final Class<T> cls) {
        if (isAbstract(cls.getModifiers())) {
            throw new RuntimeException("Cannot instantiate the constructor of an abstract class: " + cls.getName());
        }
    }
}
