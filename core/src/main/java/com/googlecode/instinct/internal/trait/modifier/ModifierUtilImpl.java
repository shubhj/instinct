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
        final int modifiers = getModifiers(member);
        return Modifier.isPublic(modifiers);
    }

    public boolean isProtected(final Member member) {
        final int modifiers = getModifiers(member);
        return Modifier.isProtected(modifiers);
    }

    public boolean isPrivate(final Member member) {
        final int modifiers = getModifiers(member);
        return Modifier.isPrivate(modifiers);
    }

    public boolean isPublicInstance(final Member member) {
        final boolean isStatic = isStatic(member);
        return !isStatic && isPublic(member);
    }

    public boolean isFinal(final Member member) {
        final int modifiers = getModifiers(member);
        return isFinal(modifiers);
    }

    public boolean isStatic(final Member member) {
        final int modifiers = getModifiers(member);
        return isStatic(modifiers);
    }

    public boolean isInstance(final Member member) {
        return !isStatic(member);
    }

    public boolean isSynchronized(final Member member) {
        final int modifiers = getModifiers(member);
        return isSynchronized(modifiers);
    }

    public <T> boolean isPublic(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return isPublic(modifiers);
    }

    public <T> boolean isFinal(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return isFinal(modifiers);
    }

    public <T> boolean isAbstract(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return isAbstract(modifiers);
    }

    public <T> boolean isConcrete(final Class<T> cls) {
        return !isAbstract(cls);
    }

    public <T> boolean isInterface(final Class<T> cls) {
        final int modifiers = cls.getModifiers();
        return isInterface(modifiers);
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

    private boolean isPublic(final int modifiers) {
        return Modifier.isPublic(modifiers);
    }

    private boolean isFinal(final int modifiers) {
        return Modifier.isFinal(modifiers);
    }

    private boolean isStatic(final int modifiers) {
        return Modifier.isStatic(modifiers);
    }

    private boolean isAbstract(final int modifiers) {
        return Modifier.isAbstract(modifiers);
    }

    private boolean isInterface(final int modifiers) {
        return Modifier.isInterface(modifiers);
    }

    private boolean isSynchronized(final int modifiers) {
        return Modifier.isSynchronized(modifiers);
    }

    private int getModifiers(final Member member) {
        return member.getModifiers();
    }
}
