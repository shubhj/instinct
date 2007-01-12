package com.googlecode.instinct.internal.util;

import au.net.netstorm.boost.util.nullo.DefaultNullMaster;
import au.net.netstorm.boost.util.nullo.NullMaster;

public final class ParamChecker {
    private static final NullMaster NULL_MASTER = new DefaultNullMaster();

    private ParamChecker() {
        throw new UnsupportedOperationException();
    }

    public static void checkNotNull(final Object... params) {
        NULL_MASTER.check(params);
    }

    public static <T> void checkIsInterface(final Class<T> type) {
        if (!type.isInterface()) {
            throw new IllegalArgumentException(type.getSimpleName() + " must be an interface not a concrete class");
        }
    }

    public static <T> void checkIsConcreteClass(final Class<T> type) {
        if (type.isInterface()) {
            throw new IllegalArgumentException(type.getSimpleName() + " must be a concrete class not an interface");
        }
    }
}
