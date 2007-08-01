package com.googlecode.instinct.test.checker;

import au.net.netstorm.boost.test.reflect.checker.DefaultModifierTestChecker;
import au.net.netstorm.boost.test.reflect.checker.ModifierTestChecker;

public final class ModifierChecker {
    private static final ModifierTestChecker MODIFIER_CHECKER = new DefaultModifierTestChecker();

    public static <T> void checkPublic(final Class<T> cls) {
        MODIFIER_CHECKER.checkPublic(cls);
    }

    public static <T> void checkFinal(final Class<T> cls) {
        MODIFIER_CHECKER.checkFinal(cls);
    }
}
