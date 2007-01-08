package au.id.adams.instinct.test.suite;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class AllTestSuite {
    private AllTestSuite() {
        throw new UnsupportedOperationException();
    }

    public static Test suite() {
        final TestSuite suite = new TestSuite("All");
        suite.addTest(AtomicTestSuite.suite());
        suite.addTest(SlowTestSuite.suite());
        return suite;
    }
}
