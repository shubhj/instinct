package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;

public final class BehaviourContextAggregatorSlowTest extends InstinctTestCase {
    private static final int EXPECTED_CONTEXTS = 14;
    private BehaviourContextAggregator aggregator;

    public void testFindsCorrectNumberOfContexts() {
        final JavaClassName[] contexts = aggregator.getContextNames();
        assertEquals(EXPECTED_CONTEXTS, contexts.length);
    }

    @Override
    public void setUpSubject() {
        aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorSlowTest.class, new ClassLocatorImpl());
    }
}
