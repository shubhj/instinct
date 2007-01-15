package com.googlecode.instinct.internal.testdouble;

import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.core.naming.DummyNamingConvention;
import com.googlecode.instinct.core.naming.NamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import static com.googlecode.instinct.mock.Mocker.anything;
import static com.googlecode.instinct.mock.Mocker.eq;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class TestDoubleAutoWirerImplAtomicTest extends InstinctTestCase {
    private TestDoubleAutoWirer wirer;
    private MarkedFieldLocator fieldLocator;
    private ObjectFactory objectFactory;
    private NamingConvention dummyNamingConvention;

    public void testProperties() {
        checkClass(TestDoubleAutoWirerImpl.class, TestDoubleAutoWirer.class);
    }

    public void testWire() {
        expects(objectFactory).method("create").with(same(DummyNamingConvention.class), eq(new Object[]{})).will(returnValue(dummyNamingConvention));
        expects(fieldLocator).method("locateAll").with(anything(), same(Dummy.class), same(dummyNamingConvention));
        wirer.wire(mock(Object.class));
    }

    @Override
    public void setUpTestDoubles() {
        fieldLocator = mock(MarkedFieldLocator.class);
        objectFactory = mock(ObjectFactory.class);
        dummyNamingConvention = mock(NamingConvention.class, "mockDummyNamingConvention");
    }

    @Override
    public void setUpSubject() {
        wirer = new TestDoubleAutoWirerImpl();
        insertFieldValue(wirer, "objectFactory", objectFactory);
        insertFieldValue(wirer, "fieldLocator", fieldLocator);
    }
}
