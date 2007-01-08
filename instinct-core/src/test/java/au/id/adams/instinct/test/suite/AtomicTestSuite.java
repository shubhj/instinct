package au.id.adams.instinct.test.suite;

import au.net.netstorm.boost.test.aggregator.DefaultTestAggregator;
import au.net.netstorm.boost.test.aggregator.TestAggregator;
import junit.framework.Test;

public final class AtomicTestSuite {
    private static final TestAggregator AGGREGATOR = new DefaultTestAggregator(AtomicTestSuite.class);

    private AtomicTestSuite() {
        throw new UnsupportedOperationException();
    }

    public static Test suite() {
        return AGGREGATOR.aggregate("Atomic", ".*AtomicTest");
    }
}
