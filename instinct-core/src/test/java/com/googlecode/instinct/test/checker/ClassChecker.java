package com.googlecode.instinct.test.checker;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import au.net.netstorm.boost.nursery.reflect.checker.ConstructorEmptyStringChecker;
import au.net.netstorm.boost.nursery.reflect.checker.ConstructorNullParameterTestChecker;
import au.net.netstorm.boost.nursery.reflect.checker.DefaultConstructorEmptyStringChecker;
import au.net.netstorm.boost.nursery.reflect.checker.DefaultConstructorNullParameterTestChecker;
import au.net.netstorm.boost.nursery.reflect.checker.DefaultMethodEmptyStringParameterTestChecker;
import au.net.netstorm.boost.nursery.reflect.checker.DefaultMethodNullParameterTestChecker;
import au.net.netstorm.boost.nursery.reflect.checker.MethodEmptyStringParameterTestChecker;
import au.net.netstorm.boost.nursery.reflect.checker.MethodNullParameterTestChecker;
import au.net.netstorm.boost.test.reflect.checker.ClassTestChecker;
import au.net.netstorm.boost.test.reflect.checker.DefaultClassTestChecker;
import au.net.netstorm.boost.test.reflect.checker.DefaultModifierTestChecker;
import au.net.netstorm.boost.test.reflect.checker.ModifierTestChecker;
import com.googlecode.instinct.internal.mock.instance.UberInstanceProvider;

public final class ClassChecker {
    private static final ClassTestChecker CLASS_CHECKER = new DefaultClassTestChecker();
    private static final ModifierTestChecker MODIFIER_CHECKER = new DefaultModifierTestChecker();
    private static final InstanceProvider INSTANCE_PROVIDER = new UberInstanceProvider();
    private static final ConstructorNullParameterTestChecker CONSTRUCTOR_NULL_CHECKER = new DefaultConstructorNullParameterTestChecker(
            INSTANCE_PROVIDER);
    private static final ConstructorEmptyStringChecker CONSTRUCTOR_EMPTY_STRING_CHECKER = new DefaultConstructorEmptyStringChecker(INSTANCE_PROVIDER);
    private static final MethodNullParameterTestChecker METHOD_NULL_CHECKER = new DefaultMethodNullParameterTestChecker(INSTANCE_PROVIDER);
    private static final MethodEmptyStringParameterTestChecker METHOD_EMPTY_STRING_CHECKER = new DefaultMethodEmptyStringParameterTestChecker(
            INSTANCE_PROVIDER);

    private ClassChecker() {
        throw new UnsupportedOperationException();
    }

    public static <U, T extends U> void checkClass(final Class<T> implementationClass, final Class<U> targetInterface) {
        checkProperties(targetInterface, implementationClass);
        nullCheckParameters(implementationClass);
        emptyStringCheckParamters(implementationClass);
    }

    public static <U, T extends U> void checkProperties(final Class<U> targetInterface, final Class<T> implementationClass) {
        MODIFIER_CHECKER.checkPublic(targetInterface);
        MODIFIER_CHECKER.checkPublic(implementationClass);
        MODIFIER_CHECKER.checkFinal(implementationClass);
        CLASS_CHECKER.checkImplementsAndFinal(targetInterface, implementationClass);
    }

    public static <U, T extends U> void checkPropertiesSuperClass(final Class<U> superClass, final Class<T> subClass) {
        MODIFIER_CHECKER.checkPublic(superClass);
        MODIFIER_CHECKER.checkPublic(subClass);
        MODIFIER_CHECKER.checkFinal(subClass);
        CLASS_CHECKER.checkSubclassOf(subClass, superClass);
    }

    private static <U, T extends U> void nullCheckParameters(final Class<T> implementationClass) {
        checkPublicConstructorsRejectNull(implementationClass);
        checkPublicMethodsRejectNull(newInstance(implementationClass));
    }

    private static <U, T extends U> void emptyStringCheckParamters(final Class<T> implementationClass) {
        checkPublicConstructorsRejectEmptyString(implementationClass);
        checkPublicMethodsRejectEmptyString(newInstance(implementationClass));
    }

    private static <T> void checkPublicConstructorsRejectEmptyString(final Class<T> classToCheck) {
        CONSTRUCTOR_EMPTY_STRING_CHECKER.checkPublicConstructorsRejectEmptyString(classToCheck);
    }

    private static <T> void checkPublicConstructorsRejectNull(final Class<T> classToCheck) {
        CONSTRUCTOR_NULL_CHECKER.checkPublicConstructorsRejectNull(classToCheck);
    }

    private static void checkPublicMethodsRejectNull(final Object instance) {
        METHOD_NULL_CHECKER.checkPublicMethodsRejectNull(instance);
    }

    private static void checkPublicMethodsRejectEmptyString(final Object instance) {
        METHOD_EMPTY_STRING_CHECKER.checkPublicMethodsRejectEmptyString(instance);
    }

    private static <T> Object newInstance(final Class<T> classToCheck) {
        return INSTANCE_PROVIDER.newInstance(classToCheck);
    }
}
