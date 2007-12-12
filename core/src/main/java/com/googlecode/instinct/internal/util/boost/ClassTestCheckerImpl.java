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

import junit.framework.Assert;

public final class ClassTestCheckerImpl implements ClassTestChecker {
    private final ModifierTestChecker modifier = new ModifierTestCheckerImpl();
    private final ClassTestUtil classes = new ClassTestUtilImpl();

    private <U, T extends U> void checkImplementsAndFinal(final Interface<U> expectedInterface, final Class<T> implementationClass) {
        modifier.checkFinal(implementationClass);
        checkImplementsInterface(expectedInterface, implementationClass);
    }

    public <U, T extends U> void checkImplementsAndFinal(final Class<U> expectedInterface, final Class<T> implementationClass) {
        final Interface<U> iface = new InterfaceImpl<U>(expectedInterface);
        checkImplementsAndFinal(iface, implementationClass);
    }

    public <U, T extends U> void checkSubclassOf(final Class<T> subClass, final Class<U> superClass) {
        final boolean isSubclass = classes.isSubclassOf(superClass, subClass);
        checkSubclassOf(isSubclass, superClass, subClass);
    }

    private <U, T extends U> void checkSubclassOf(final boolean isSubclass, final Class<U> superClass, final Class<T> subClass) {
        final String superClassName = superClass.getSimpleName();
        final String subClassName = subClass.getSimpleName();
        Assert.assertTrue(subClassName + " is not a subclass of " + superClassName + ".", isSubclass);
    }

    private <T> void checkImplementsInterface(final Interface iface, final Class<T> cls) {
        if (!classes.isImplementationOf(iface, cls)) {
            throw new AssertionError(cls.getSimpleName() + " must be an implementation of " + iface.getType().getSimpleName());
        }
    }
}
