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

import com.googlecode.instinct.internal.util.lang.Primordial;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;

public final class ClassMethodTestUtilImpl implements ClassMethodTestUtil {
    private final Collection inherited = new HashSet();
    private ModifierTestUtil modifierUtil = new ModifierTestUtilImpl();

    {
        inherited.add("toString");
        inherited.add("hashCode");
        inherited.add("equals");
    }

    public Method[] getAll(final Class cls) {
        return guardGetAll(cls);
    }

    public Method[] getAllPublicInstance(final Class cls) {
        final Set result = getAllAsSet(cls);
        keepPublicInstance(result);
        return methods(result);
    }

    public Method[] getAllNonInherited(final Class cls) {
        final Set result = getAllAsSet(cls);
        keepNonInherited(result);
        return methods(result);
    }

    public Method[] getAllNotInheritedPublicInstance(final Class cls) {
        final Set result = getAllAsSet(cls);
        keepNonInherited(result);
        keepPublicInstance(result);
        return methods(result);
    }

    private Set getAllAsSet(final Class cls) {
        final Method[] all = guardGetAll(cls);
        final List list = Arrays.asList(all);
        return new HashSet(list);
    }

    private Method[] guardGetAll(final Class cls) {
        final Class superclass = cls.getSuperclass();
        if (!superclass.equals(Primordial.class)) {
            Assert.fail("Currently we only support Primordial as a superclass.  You requested " + superclass);
        }
        return cls.getDeclaredMethods();
    }

    private void keepPublicInstance(final Collection set) {
        final Method[] methods = methods(set);
        for (final Member method : methods) {
            keepPublicInstance(set, method);
        }
    }

    private void keepNonInherited(final Collection set) {
        final Method[] methods = methods(set);
        for (final Member method : methods) {
            keepNonInherited(set, method);
        }
    }

    private void keepPublicInstance(final Collection set, final Member method) {
        if (!modifierUtil.isPublicInstance(method)) {
            set.remove(method);
        }
    }

    private void keepNonInherited(final Collection set, final Member method) {
        final String name = method.getName();
        if (inherited.contains(name)) {
            set.remove(method);
        }
    }

    private Method[] methods(final Collection set) {
        return (Method[]) set.toArray(new Method[]{});
    }
}
