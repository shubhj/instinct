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
}
