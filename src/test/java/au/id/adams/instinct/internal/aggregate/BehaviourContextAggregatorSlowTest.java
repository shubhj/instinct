package au.id.adams.instinct.internal.aggregate;

import au.id.adams.instinct.internal.aggregate.locate.ClassLocator;
import au.id.adams.instinct.internal.aggregate.locate.ClassLocatorImpl;
import au.id.adams.instinct.test.InstinctTestCase;
import au.id.adams.instinct.internal.util.ClassName;
import au.id.adams.instinct.internal.aggregate.BehaviourContextAggregator;
import au.id.adams.instinct.internal.aggregate.BehaviourContextAggregatorImpl;

public final class BehaviourContextAggregatorSlowTest extends InstinctTestCase {
    private static final int EXPECTED_CONTEXTS = 17;
    private BehaviourContextAggregator aggregator;

    public void testFindsCorrectNumberOfContexts() {
        final ClassName[] contexts = aggregator.getContexts();
        assertEquals(EXPECTED_CONTEXTS, contexts.length);
    }

    @Override
    public void setUpSubjects() {
        final ClassLocator locator = new ClassLocatorImpl();
        aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorSlowTest.class, locator);
    }
}
