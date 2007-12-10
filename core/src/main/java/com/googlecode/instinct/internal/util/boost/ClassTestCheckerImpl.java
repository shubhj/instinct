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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import junit.framework.Assert;

public final class ClassTestCheckerImpl implements ClassTestChecker {
    private static final String[] EXCLUSIONS = {"hashCode", "getClass", "equals", "toString", "wait", "notify", "notifyAll"};
    private final ModifierTestChecker modifier = new ModifierTestCheckerImpl();
    private final ClassTestUtil classes = new ClassTestUtilImpl();

    public <U, T extends U> void checkImplementsAndFinal(final Interface<U> expectedInterface, final Class<T> implementationClass) {
        modifier.checkFinal(implementationClass);
        checkImplementsInterface(expectedInterface, implementationClass);
    }

    public <U, T extends U> void checkImplementsAndFinal(final Class<U> expectedInterface, final Class<T> implementationClass) {
        final Interface<U> iface = new InterfaceImpl<U>(expectedInterface);
        checkImplementsAndFinal(iface, implementationClass);
    }

    public void checkSubInterfaceOf(final Interface subInterface, final Interface superInterface) {
        final boolean isSubInterface = classes.isSubInterfaceOf(superInterface, subInterface);
        Assert.assertTrue(subInterface + " is not subinterface of  " + superInterface + ".", isSubInterface);
    }

    public <T> void checkSubclassOfRef(final Class<T> expectedImpl, final Object ref) {
        checkNotNull(expectedImpl, ref);
        Assert.assertNotNull(ref);
        final Class cls = ref.getClass();
        checkSubclassOf(cls, expectedImpl);
    }

    public <U, T extends U> void checkSubclassOf(final Class<T> subClass, final Class<U> superClass) {
        final boolean isSubclass = classes.isSubclassOf(superClass, subClass);
        checkSubclassOf(isSubclass, superClass, subClass);
    }

    public <T> void checkSynchronized(final Class<T> cls) {
        final Method[] methods = cls.getMethods();
        for (final Method method : methods) {
            checkSynchronizedIgnoringExclusions(method);
        }
    }

    private <U, T extends U> void checkSubclassOf(final boolean isSubclass, final Class<U> superClass, final Class<T> subClass) {
        final String superClassName = superClass.getSimpleName();
        final String subClassName = subClass.getSimpleName();
        Assert.assertTrue(subClassName + " is not a subclass of " + superClassName + ".", isSubclass);
    }

    private <T> void checkImplementsInterface(final Interface iface, final Class<T> cls) {
        final boolean implementsIt = classes.isImplementationOf(iface, cls);
        checkImplementsInterface(implementsIt, iface, cls);
    }

    private <T> void checkImplementsInterface(final boolean implementsIt, final Interface iface, final Class<T> cls) {
        final String implName = cls.getSimpleName();
        final String targetName = iface.getType().getSimpleName();
        Assert.assertTrue(implName + " is not an implementation of " + targetName + ".", implementsIt);
    }

    private void checkSynchronizedIgnoringExclusions(final Method method) {
        final boolean isExclusion = isExclusion(method);
        if (!isExclusion) {
            modifier.checkSynchronized(method);
        }
    }

    private boolean isExclusion(final Member method) {
        final String name = method.getName();
        return isExclusion(name);
    }

    private boolean isExclusion(final Object methodName) {
        for (final Object exclusion : EXCLUSIONS) {
            if (methodName.equals(exclusion)) {
                return true;
            }
        }
        return false;
    }
}
