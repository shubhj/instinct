/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.locate.field;

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Field;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ANamedFieldLocatorWithNothingNoFieldsToFind {
    @Subject private NamedFieldLocator locator;

    @Specification
    public void conformsToClassTraits() {
        checkClass(NamedFieldLocatorImpl.class, NamedFieldLocator.class);
    }

    private Field getField(final String fieldName) {
        return getFieldByName(WithoutNamedFields.class, fieldName);
    }

    @SuppressWarnings({"ALL"})
    private static final class WithoutNamedFields {
    }
}