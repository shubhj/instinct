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

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.MockNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.checker.ExceptionTestChecker;
import java.lang.reflect.Field;
import java.util.Collection;

public final class MarkedFieldLocatorImplAtomicTest extends InstinctTestCase {
    @Subject private MarkedFieldLocator fieldLocator;
    @Stub(auto = false) private MarkingScheme markingScheme;

    @Override
    public void setUpTestDoubles() {
        markingScheme = new MarkingSchemeImpl(Mock.class, new MockNamingConvention(), IGNORE);
    }

    @Override
    public void setUpSubject() {
        fieldLocator = new MarkedFieldLocatorImpl();
    }

    public void testConformsToClassTraits() {
        checkClass(MarkedFieldLocatorImpl.class, MarkedFieldLocator.class);
    }

    public void testFindsAnnotatedAndNamedFields() {
        final Iterable<Field> fields = fieldLocator.locateAll(WithAnnotatedAndNamedFields.class, markingScheme);
        expect.that(fields).isNotEmpty();
        expect.that(fields).containsItem(field("mockCharSequence"));
        expect.that(fields).containsItem(field("mockAnotherCharSequence"));
    }

    public void testReturnsAnUnmodifiableCollection() {
        ExceptionTestChecker.expectException(UnsupportedOperationException.class, new Runnable() {
            public void run() {
                final Collection<Field> fields = fieldLocator.locateAll(WithAnnotatedAndNamedFields.class, markingScheme);
                fields.clear();
            }
        });
    }

    public void testDoesNotReturnTheSameFieldTwice() {
        final MarkingScheme scheme = new MarkingSchemeImpl(Mock.class, new MockNamingConvention(), IGNORE);
        final Collection<Field> fields = new MarkedFieldLocatorImpl().locateAll(WithAnnotatedAndNamedFields.class, scheme);
        expect.that(fields).isOfSize(2);
    }

    private Field field(final String fieldName) {
        return getFieldByName(WithAnnotatedAndNamedFields.class, fieldName);
    }

    @SuppressWarnings({"ALL"})
    private static final class WithAnnotatedAndNamedFields {
        @Mock CharSequence mockCharSequence;
        CharSequence mockAnotherCharSequence;
    }
}
