package au.id.adams.instinct.verify;

import static java.text.MessageFormat.format;
import au.id.adams.instinct.internal.util.Suggest;

public final class Verify {

    private Verify() {
        throw new UnsupportedOperationException();
    }

    @Suggest("This code belongs in a utility, delegated to from here")
    public static void mustEqual(final Object expected, final Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        fail(format("Must be: <{0}>, but was: <{1}>", expected, actual));
    }

    @Suggest("This code belongs in a utility, delegated to from here")
    public static void mustBeTrue(final boolean test) {
        if (!test) {
            fail("Must be true");
        }
    }

    @Suggest("This code belongs in a utility, delegated to from here")
    public static void mustBeFalse(final boolean test) {
        if (test) {
            fail("Must be false");
        }
    }

    private static void fail(final String message) {
        throw new VerificationException(message);
    }
}
