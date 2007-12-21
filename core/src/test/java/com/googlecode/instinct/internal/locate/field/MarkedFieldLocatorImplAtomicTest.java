/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.locate.field;

import com.googlecode.instinct.expect.Expect;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.MockNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.checker.ExceptionTestChecker;
import java.lang.reflect.Field;
import java.util.Collection;
import static java.util.Collections.emptyList;
import org.jmock.Expectations;

public final class MarkedFieldLocatorImplAtomicTest extends InstinctTestCase {
    @Subject private MarkedFieldLocator fieldLocator;
    @Mock private AnnotatedFieldLocator annotatedFieldLocator;
    @Mock private NamedFieldLocator namedFieldLocator;
    @Mock private MarkingScheme markingScheme;
    @Stub(auto = false) private Collection<Field> annotatedFields;
    @Stub(auto = false) private Collection<Field> namedFields;
    @Dummy private Dummy annotationType;
    @Dummy private NamingConvention namingConvention;

    @Override
    public void setUpTestDoubles() {
        annotatedFields = emptyList();
        namedFields = emptyList();
    }

    @Override
    public void setUpSubject() {
        fieldLocator = createSubject(MarkedFieldLocatorImpl.class, annotatedFieldLocator, namedFieldLocator);
    }

    public void testConformsToClassTraits() {
        checkClass(MarkedFieldLocatorImpl.class, MarkedFieldLocator.class);
    }

    public void testUsesAnnotatedLocatorAndNamedLocatorToFindMarkedFields() {
        expect.that(new Expectations() {
            {
                one(markingScheme).getAnnotationType();
                will(returnValue(annotationType.getClass()));
                one(annotatedFieldLocator).locate(WithAnnotatedAndNamedFields.class, annotationType.getClass());
                will(returnValue(annotatedFields));
                one(markingScheme).getNamingConvention();
                will(returnValue(namingConvention));
                one(namedFieldLocator).locate(WithAnnotatedAndNamedFields.class, namingConvention);
                will(returnValue(namedFields));
            }
        });
        final Iterable<Field> fields = fieldLocator.locateAll(WithAnnotatedAndNamedFields.class, markingScheme);
        expect.that(fields).containsItems(annotatedFields);
        expect.that(fields).containsItems(namedFields);
    }

    public void testReturnsAnUnmodifiableCollection() {
        ExceptionTestChecker.expectException(UnsupportedOperationException.class, new Runnable() {
            public void run() {
                final MarkingScheme scheme = new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention(), IGNORE);
                final Collection<Field> fields = new MarkedFieldLocatorImpl().locateAll(WithAnnotatedAndNamedFields.class, scheme);
                fields.clear();
            }
        });
    }

    public void testDoesNotReturnTheSameFieldTwice() {
        final MarkingScheme scheme = new MarkingSchemeImpl(Mock.class, new MockNamingConvention(), IGNORE);
        final Collection<Field> fields = new MarkedFieldLocatorImpl().locateAll(WithAnnotatedAndNamedFields.class, scheme);
        Expect.expect.that(fields).isOfSize(1);
    }

    @SuppressWarnings({"ALL"})
    private static final class WithAnnotatedAndNamedFields {
        @Mock CharSequence mockCharSequence;
    }
}
