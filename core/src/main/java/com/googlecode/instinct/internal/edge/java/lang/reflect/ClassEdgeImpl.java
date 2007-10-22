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

package com.googlecode.instinct.internal.edge.java.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.EdgeException;

public final class ClassEdgeImpl implements ClassEdge {
    public Class<?> forName(final String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new EdgeException(e);
        }
    }

    public <T> T newInstance(final Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new EdgeException(e);
        } catch (IllegalAccessException e) {
            throw new EdgeException(e);
        }
    }

    public <T> Constructor<T> getConstructor(final Class<T> cls, final Class<?>... parameterTypes) {
        try {
            return cls.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new EdgeException(e);
        }
    }

    public <T> Constructor<T> getDeclaredConstructor(final Class<T> cls, final Class<?>... parameterTypes) {
        try {
            return cls.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new EdgeException(e);
        }
    }

    public <T> Method getMethod(final Class<T> cls, final String methodName, final Class<?>... parameterTypes) {
        try {
            return cls.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new EdgeException(e);
        }
    }

    public <T> Method getDeclaredMethod(final Class<T> cls, final String methodName, final Class<?>... parameterTypes) {
        try {
            return cls.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new EdgeException(e);
        }
    }

    public <T> Field getDeclaredField(final Class<T> cls, final String fieldName) {
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new EdgeException(e);
        }
    }
}
