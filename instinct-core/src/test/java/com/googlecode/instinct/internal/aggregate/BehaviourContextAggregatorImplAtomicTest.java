package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.mock.Mockery.anything;
import static com.googlecode.instinct.test.mock.Mockery.expects;
import static com.googlecode.instinct.test.mock.Mockery.mock;
import static com.googlecode.instinct.test.mock.Mockery.once;
import static com.googlecode.instinct.test.mock.Mockery.returnValue;

public final class BehaviourContextAggregatorImplAtomicTest extends InstinctTestCase {
    private JavaClassName[] classNames;
    private ClassLocator classLocator;

    public void test() {
        classNames = new JavaClassName[]{mock(JavaClassName.class)};
        classLocator = mock(ClassLocator.class);
        expects(classLocator, once()).method("locate").with(anything(), anything()).will(returnValue(classNames));
        final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorImplAtomicTest.class,
                classLocator);
        final JavaClassName[] names = aggregator.getContextNames();
        assertNotNull(names);
    }
}
