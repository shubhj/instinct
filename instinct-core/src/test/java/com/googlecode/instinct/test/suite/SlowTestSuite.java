package com.googlecode.instinct.test.suite;

import au.net.netstorm.boost.test.aggregator.DefaultTestAggregator;
import au.net.netstorm.boost.test.aggregator.TestAggregator;
import junit.framework.Test;

public final class SlowTestSuite {
    private static final TestAggregator AGGREGATOR = new DefaultTestAggregator(SlowTestSuite.class);

    private SlowTestSuite() {
        throw new UnsupportedOperationException();
    }

    public static Test suite() {
        return AGGREGATOR.aggregate("Slow", ".*SlowTest");
    }
}
