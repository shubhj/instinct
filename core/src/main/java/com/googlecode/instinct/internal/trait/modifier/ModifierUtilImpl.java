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

package com.googlecode.instinct.internal.trait.modifier;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ModifierUtilImpl implements ModifierUtil {
    public boolean isPublic(final Member member) {
        return Modifier.isPublic(member.getModifiers());
    }

    public boolean isProtected(final Member member) {
        return Modifier.isProtected(member.getModifiers());
    }

    public boolean isPrivate(final Member member) {
        return Modifier.isPrivate(member.getModifiers());
    }

    public boolean isPublicInstance(final Member member) {
        final boolean isStatic = isStatic(member);
        return !isStatic && isPublic(member);
    }

    public boolean isFinal(final Member member) {
        return Modifier.isFinal(member.getModifiers());
    }

    public boolean isStatic(final Member member) {
        return Modifier.isStatic(member.getModifiers());
    }

    public boolean isInstance(final Member member) {
        return !isStatic(member);
    }

    public boolean isSynchronized(final Member member) {
        return Modifier.isSynchronized(member.getModifiers());
    }

    public <T> boolean isPublic(final Class<T> cls) {
        return Modifier.isPublic(cls.getModifiers());
    }

    public <T> boolean isFinal(final Class<T> cls) {
        return Modifier.isFinal(cls.getModifiers());
    }

    public <T> boolean isAbstract(final Class<T> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }

    public <T> boolean isConcrete(final Class<T> cls) {
        return !isAbstract(cls);
    }

    public <T> boolean isInterface(final Class<T> cls) {
        return Modifier.isInterface(cls.getModifiers());
    }

    public <T> boolean isSynchronized(final Class<T> cls) {
        final Method[] methods = cls.getMethods();
        for (final Member method : methods) {
            if (!isSynchronized(method)) {
                return false;
            }
        }
        return true;
    }
}
