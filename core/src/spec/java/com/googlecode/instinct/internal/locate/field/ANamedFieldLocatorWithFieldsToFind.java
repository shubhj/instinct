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
import com.googlecode.instinct.marker.naming.NamingConvention;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Field;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ANamedFieldLocatorWithFieldsToFind {
    @Subject(implementation = NamedFieldLocatorImpl.class) private NamedFieldLocator locator;
    @Stub(auto = false) private NamingConvention namingConvention;

    @BeforeSpecification
    public void before() {
        locator = createSubject(NamedFieldLocatorImpl.class);
        namingConvention = new FooNamingConvention();
    }

    @Specification
    public void conformsToClassTraits() {
        checkClass(NamedFieldLocatorImpl.class, NamedFieldLocator.class);
    }

    @Specification
    public void findFieldsMarkedWithANamingConvention() {
        final Iterable<Field> fields = locator.locate(WithNamedFields.class, namingConvention);
        expect.that(fields).containsItem(getField("fooString1"));
        expect.that(fields).containsItem(getField("fooString2"));
    }

    private Field getField(final String fieldName) {
        return getFieldByName(WithNamedFields.class, fieldName);
    }

    @SuppressWarnings({"ALL"})
    private static final class WithNamedFields {
        private final String fooString1 = "";
        private String fooString2;
    }

    private static final class FooNamingConvention implements NamingConvention {
        public String getPattern() {
            return "^foo.*";
        }
    }
}