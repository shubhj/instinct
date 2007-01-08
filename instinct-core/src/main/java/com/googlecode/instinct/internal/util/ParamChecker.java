package com.googlecode.instinct.internal.util;

import au.net.netstorm.boost.util.nullo.DefaultNullMaster;

public final class ParamChecker {
    private static final DefaultNullMaster nullMaster = new DefaultNullMaster();

    private ParamChecker() {
        throw new UnsupportedOperationException();
    }

    public static void checkNotNull(final Object... params) {
        nullMaster.check(params);
    }
}
