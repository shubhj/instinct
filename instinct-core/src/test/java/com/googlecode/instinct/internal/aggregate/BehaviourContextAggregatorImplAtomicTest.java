package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.mock.Mocker.anything;
import static com.googlecode.instinct.test.mock.Mocker.mock;
import static com.googlecode.instinct.test.mock.Mocker.mockController;
import static com.googlecode.instinct.test.mock.Mocker.once;
import static com.googlecode.instinct.test.mock.Mocker.returnValue;
import org.jmock.Mock;

public final class BehaviourContextAggregatorImplAtomicTest extends InstinctTestCase {
    public void test() {
        final JavaClassName[] classNames = {mock(JavaClassName.class)};
        final Mock locatorController = mockController(ClassLocator.class);
        locatorController.expects(once()).method("locate").with(anything(), anything()).will(returnValue(classNames));
        final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorImplAtomicTest.class,
                (ClassLocator) locatorController.proxy());
        final JavaClassName[] names = aggregator.getContextNames();
        assertNotNull(names);
    }
}
