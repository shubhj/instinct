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

package com.googlecode.instinct.test.checker;

import com.googlecode.instinct.internal.trait.cls.ClassTraitChecker;
import com.googlecode.instinct.internal.trait.cls.ClassTraitCheckerImpl;
import com.googlecode.instinct.internal.trait.param.ConstructorNullParameterTestChecker;
import com.googlecode.instinct.internal.trait.param.ConstructorNullParameterTestCheckerImpl;
import com.googlecode.instinct.internal.trait.param.MehodNullParameterTestChecker;
import com.googlecode.instinct.internal.trait.param.MehodNullParameterTestCheckerImpl;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.instance.GenericInstanceProvider;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkFinal;
import static com.googlecode.instinct.test.checker.ModifierChecker.checkPublic;

public final class ClassChecker {
    private static final ClassTraitChecker CLASS_CHECKER = new ClassTraitCheckerImpl();
    @Fix("This returns mocks - which aint much use when testing a specific implementation see StateExpectationsImplAtomicTest.")
    private static final InstanceProvider INSTANCE_PROVIDER = new GenericInstanceProvider();
    private static final ConstructorNullParameterTestChecker CONSTRUCTOR_NULL_CHECKER =
            new ConstructorNullParameterTestCheckerImpl(INSTANCE_PROVIDER);
    private static final MehodNullParameterTestChecker METHOD_NULL_CHECKER = new MehodNullParameterTestCheckerImpl(INSTANCE_PROVIDER);

    private ClassChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T> void checkClass(final Class<T> implementationClass) {
        checkPublic(implementationClass);
        checkFinal(implementationClass);
        nullCheckParameters(implementationClass);
    }

    @Suggest("Infer the interface/parent type from the concrete class.")
    public static <U, T extends U> void checkClass(final Class<T> implementationClass, final Class<U> parentType) {
        checkClassProperties(implementationClass, parentType);
        nullCheckParameters(implementationClass);
    }

    @Suggest("Infer the interface from the concrete class.")
    public static <U, T extends U> void checkClassWithoutParamChecks(final Class<T> subClass, final Class<U> superClass) {
        checkClassProperties(subClass, superClass);
    }

    public static <T> void checkInterface(final Class<T> iface) {
        checkPublic(iface);
    }

    public static <U, T extends U> void nullCheckParameters(final Class<T> implementationClass) {
        checkPublicConstructorsRejectNull(implementationClass);
        checkPublicMethodsRejectNull(newInstance(implementationClass));
    }

    public static <T> void checkPublicConstructorsRejectNull(final Class<T> classToCheck) {
        CONSTRUCTOR_NULL_CHECKER.checkPublicConstructorsRejectNull(classToCheck);
    }

    public static void checkPublicMethodsRejectNull(final Object instance) {
        METHOD_NULL_CHECKER.checkPublicMethodsRejectNull(instance);
    }

    private static <U, T extends U> void checkClassProperties(final Class<T> implementationClass, final Class<U> parentType) {
        checkPublic(parentType);
        checkPublic(implementationClass);
        checkFinal(implementationClass);
        if (parentType.isInterface()) {
            CLASS_CHECKER.checkImplementsAndFinal(parentType, implementationClass);
        } else {
            CLASS_CHECKER.checkSubclassOf(implementationClass, parentType);
        }
        // check serialisability of classes that claim it.
        // check methods that return collections are unmodifiable
    }

    private static <T> Object newInstance(final Class<T> classToCheck) {
        return INSTANCE_PROVIDER.newInstance(classToCheck);
    }
}
