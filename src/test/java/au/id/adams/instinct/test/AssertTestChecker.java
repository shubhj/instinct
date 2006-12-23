package au.id.adams.instinct.test;

import au.net.netstorm.boost.nursery.reflect.checker.DefaultAssertThrows;
import au.net.netstorm.boost.nursery.reflect.checker.AssertThrows;

public final class AssertTestChecker {
    private static final AssertThrows ASSERT_THROWS = new DefaultAssertThrows();

    private AssertTestChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Throwable> Throwable assertThrows(final Class<T> expectedException, final String message, final Runnable block) {
        return ASSERT_THROWS.assertThrows(expectedException, message, block);
    }

    public static <T extends Throwable> Throwable assertThrows(final Class<T> expectedException, final Runnable block) {
        return ASSERT_THROWS.assertThrows(expectedException, block);
    }

    public static void assertMessageContains(final Throwable t, final String fragment) {
        ASSERT_THROWS.assertMessageContains(t, fragment);
    }
}
