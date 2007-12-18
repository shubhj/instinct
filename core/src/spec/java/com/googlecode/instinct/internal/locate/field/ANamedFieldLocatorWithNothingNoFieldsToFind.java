/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.locate.field;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.MockNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.actor.TestSubjectCreator;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ANamedFieldLocatorWithNothingNoFieldsToFind {
    @Subject(implementation = NamedFieldLocatorImpl.class) private NamedFieldLocator locator;
    @Stub(auto = false) private NamingConvention namingConvention;

    @BeforeSpecification
    public void before() {
        locator = TestSubjectCreator.createSubject(NamedFieldLocatorImpl.class);
        namingConvention = new MockNamingConvention();
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(NamedFieldLocatorImpl.class, NamedFieldLocator.class);
    }

    @Specification
    public void doesNotFindAnything() {
        final Set<Field> fields = locator.locate(WithoutNamedFields.class, namingConvention);
        expect.that(fields).isEmpty();
    }

    @Specification(expectedException = UnsupportedOperationException.class)
    public void returnsAnUnmodifiableSetOfFields() {
        final Set<Field> fields = locator.locate(WithoutNamedFields.class, namingConvention);
        fields.clear();
    }

    private Field getField(final String fieldName) {
        return getFieldByName(WithoutNamedFields.class, fieldName);
    }

    @SuppressWarnings({"ALL"})
    private static final class WithoutNamedFields {
    }
}