package com.googlecode.instinct.test.checker;

import au.net.netstorm.boost.nursery.reflect.checker.AssertThrows;
import au.net.netstorm.boost.nursery.reflect.checker.DefaultAssertThrows;

public final class AssertThrowsChecker {
    private static final AssertThrows ASSERT_THROWS = new DefaultAssertThrows();

    private AssertThrowsChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T> Throwable assertThrows(final Class<T> expectedException, final String message, final Runnable block) {
        return ASSERT_THROWS.assertThrows(expectedException, message, block);
    }

    public static <T> Throwable assertThrows(final Class<T> expectedException, final Runnable block) {
        return ASSERT_THROWS.assertThrows(expectedException, block);
    }

    public static void assertMessageContains(final Throwable t, final String fragment) {
        ASSERT_THROWS.assertMessageContains(t, fragment);
    }
}
