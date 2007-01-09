package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.DodgyClassName;
import com.googlecode.instinct.test.InstinctTestCase;

public final class BehaviourContextAggregatorSlowTest extends InstinctTestCase {
    private static final int EXPECTED_CONTEXTS = 14;
    private BehaviourContextAggregator aggregator;

    public void testFindsCorrectNumberOfContexts() {
        final DodgyClassName[] contexts = aggregator.getContextNames();
        assertEquals(EXPECTED_CONTEXTS, contexts.length);
    }

    @Override
    public void setUpSubjects() {
        final ClassLocator locator = new ClassLocatorImpl();
        aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorSlowTest.class, locator);
    }
}
