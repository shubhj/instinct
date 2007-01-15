package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Field;
import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.core.naming.DummyNamingConvention;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class MarkedFieldLocatorImplAtomicTest extends InstinctTestCase {
    private static final Class<WithRuntimeAnnotations> SOME_CLASS = WithRuntimeAnnotations.class;
    private MarkedFieldLocator fieldLocator;
    private AnnotatedFieldLocator annotatedFieldLocator;
    private Field[] annotatedFields;

    public void testProperties() {
        checkClass(MarkedFieldLocatorImpl.class, MarkedFieldLocator.class);
    }

    public void testUsesAnnotatedLocator() {
        expects(annotatedFieldLocator).method("locate").with(same(SOME_CLASS), same(Dummy.class)).will(returnValue(annotatedFields));
        final Field[] fields = fieldLocator.locateAll(SOME_CLASS, Dummy.class, new DummyNamingConvention());
        assertSame(annotatedFields, fields);
    }

    @Override
    public void setUpTestDoubles() {
        annotatedFieldLocator = mock(AnnotatedFieldLocator.class);
        annotatedFields = new Field[]{};
    }

    @Override
    public void setUpSubject() {
        fieldLocator = new MarkedFieldLocatorImpl();
        insertFieldValue(fieldLocator, "annotatedFieldLocator", annotatedFieldLocator);
    }
}
