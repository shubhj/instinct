package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.reflect.Reflector;

@SuppressWarnings({"UnusedDeclaration"})
public final class DummyTestDoubleCreatorAtomicTest extends InstinctTestCase {
    private TestDoubleCreator creator;

    public void testProperties() {
        checkClass(DummyTestDoubleCreator.class, TestDoubleCreator.class);
    }

    @Suggest("Lyall & Tom were here!")
    public void testCreateValue() {
        final Field field = Reflector.getField(AClassIsAWonderfulThing.class, "aFieldOfGrass");
        final Object dummyValue = creator.createValue(field);
        assertNotNull(dummyValue);
    }

    @Override
    public void setUpSubject() {
        creator = new DummyTestDoubleCreator();
    }

    private static final class AClassIsAWonderfulThing {
        private String aFieldOfGrass;
    }
}
