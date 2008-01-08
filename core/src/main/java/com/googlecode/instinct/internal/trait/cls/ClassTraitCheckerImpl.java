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

package com.googlecode.instinct.internal.trait.cls;

import com.googlecode.instinct.internal.trait.modifier.ModifierTraitChecker;
import com.googlecode.instinct.internal.trait.modifier.ModifierTraitCheckerImpl;

public final class ClassTraitCheckerImpl implements ClassTraitChecker {
    private final ModifierTraitChecker modifier = new ModifierTraitCheckerImpl();

    public <U, T extends U> void checkImplementsAndFinal(final Class<U> expectedInterface, final Class<T> implementationClass) {
        modifier.checkFinal(implementationClass);
        checkImplementsInterface(expectedInterface, implementationClass);
    }

    public <U, T extends U> void checkSubclassOf(final Class<T> subClass, final Class<U> superClass) {
        if (!superClass.isAssignableFrom(subClass)) {
            throw new AssertionError(subClass.getSimpleName() + " must be a sub-class of " + superClass.getSimpleName());
        }
    }

    private <U, T extends U> void checkImplementsInterface(final Class<U> expectedInterface, final Class<T> cls) {
        if (!expectedInterface.isAssignableFrom(cls)) {
            throw new AssertionError(cls.getSimpleName() + " must be an implementation of " + expectedInterface.getSimpleName());
        }
    }
}
