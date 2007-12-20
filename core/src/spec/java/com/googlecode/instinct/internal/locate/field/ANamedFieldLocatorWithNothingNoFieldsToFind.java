/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.locate.field;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.MockNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Field;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ANamedFieldLocatorWithNothingNoFieldsToFind {
    @Subject(implementation = NamedFieldLocatorImpl.class) private NamedFieldLocator locator;
    @Stub(auto = false) private NamingConvention namingConvention;

    @BeforeSpecification
    public void before() {
        locator = createSubject(NamedFieldLocatorImpl.class);
        namingConvention = new MockNamingConvention();
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(NamedFieldLocatorImpl.class, NamedFieldLocator.class);
    }

    @Specification(expectedException = UnsupportedOperationException.class)
    public void returnsAnUnmodifiableSetOfFields() {
        final Iterable<Field> fields = locator.locate(WithoutNamedFields.class, namingConvention);
        fields.iterator().remove();
    }

    @Specification
    public void doesNotFindAnyFields() {
        final Iterable<Field> fields = locator.locate(WithoutNamedFields.class, namingConvention);
        expect.that(fields).isEmpty();
    }

    @SuppressWarnings({"ALL"})
    private static final class WithoutNamedFields {
    }
}