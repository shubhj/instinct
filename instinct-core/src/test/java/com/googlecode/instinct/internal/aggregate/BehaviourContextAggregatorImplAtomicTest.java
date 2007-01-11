package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.mock.Mocker.anything;
import static com.googlecode.instinct.test.mock.Mocker.expects;
import static com.googlecode.instinct.test.mock.Mocker.mock;
import static com.googlecode.instinct.test.mock.Mocker.once;
import static com.googlecode.instinct.test.mock.Mocker.returnValue;
import static com.googlecode.instinct.test.mock.Mocker.verify;

public final class BehaviourContextAggregatorImplAtomicTest extends InstinctTestCase {
    public void testGetContextNames() {
        // create test objects
        final JavaClassName[] classNames = {mock(JavaClassName.class)};
        final ClassLocator classLocator = mock(ClassLocator.class);
        final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorImplAtomicTest.class,
                classLocator);

        // setup expectations
        expects(classLocator, once()).method("locate").with(anything(), anything()).will(returnValue(classNames));

        //expects(classLocator).method("locate", once()).with(anything(), anything()).will(returnValue(classNames));

        // do actual work
        final JavaClassName[] names = aggregator.getContextNames();

        // run checks
        assertSame(classNames, names);
        verify();
    }
}
