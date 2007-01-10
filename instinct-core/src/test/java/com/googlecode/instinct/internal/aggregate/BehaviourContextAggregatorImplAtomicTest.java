package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.mock.Mockery.anything;
import static com.googlecode.instinct.test.mock.Mockery.expects;
import static com.googlecode.instinct.test.mock.Mockery.mock;
import static com.googlecode.instinct.test.mock.Mockery.once;
import static com.googlecode.instinct.test.mock.Mockery.returnValue;
import static com.googlecode.instinct.test.mock.Mockery.verify;

public final class BehaviourContextAggregatorImplAtomicTest extends InstinctTestCase {

    public void test() {
        final JavaClassName[] classNames = new JavaClassName[]{mock(JavaClassName.class)};
        final ClassLocator classLocator = mock(ClassLocator.class);
        expects(classLocator, once()).method("locate").with(anything(), anything()).will(returnValue(classNames));
        final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorImplAtomicTest.class,
                classLocator);
        final JavaClassName[] names = aggregator.getContextNames();
        assertSame(classNames, names);
        verify();
    }
}
