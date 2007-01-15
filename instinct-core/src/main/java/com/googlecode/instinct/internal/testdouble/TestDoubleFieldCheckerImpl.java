package com.googlecode.instinct.internal.testdouble;

import java.lang.reflect.Field;
import static java.lang.reflect.Modifier.isFinal;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeField;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeField;
import com.googlecode.instinct.core.TestDoubleConfigurationException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class TestDoubleFieldCheckerImpl implements TestDoubleFieldChecker {
    @Suggest({"Add getModifiers() to edge", "Create EdgeModifer"})
    private final EdgeField edgeField = new DefaultEdgeField();

    public void checkField(final Field field, final Object instance) {
        checkNotNull(field, instance);
        rejectFinalModifiers(field);
        rejectNonNullValue(field, instance);
    }

    private void rejectFinalModifiers(final Field field) {
        if (isFinal(field.getModifiers())) {
            throw new TestDoubleConfigurationException("Field " + field.getName() + " cannot be final");
        }
    }

    private void rejectNonNullValue(final Field field, final Object instance) {
        field.setAccessible(true);
        if (edgeField.get(field, instance) != null) {
            throw new TestDoubleConfigurationException("Field " + field.getName() + " must be null");
        }
    }
}
