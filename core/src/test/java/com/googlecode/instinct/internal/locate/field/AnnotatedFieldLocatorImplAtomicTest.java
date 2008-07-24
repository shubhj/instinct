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
import com.googlecode.instinct.internal.locate.AnnotationChecker;
import com.googlecode.instinct.internal.locate.WithRuntimeAnnotations;
import com.googlecode.instinct.internal.locate.WithoutRuntimeAnnotations;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Collection;
import org.jmock.Expectations;

@SuppressWarnings({"UnusedDeclaration"})
public final class AnnotatedFieldLocatorImplAtomicTest extends InstinctTestCase {
    @Subject private AnnotatedFieldLocator locator;
    @Mock private AnnotationChecker checker;

    @Override
    public void setUpSubject() {
        locator = createSubject(AnnotatedFieldLocatorImpl.class, checker);
    }

    public void testConformsToClassTraits() {
        checkClass(AnnotatedFieldLocatorImpl.class, AnnotatedFieldLocator.class);
    }

    public void testLocateOnAClassWithNoAnnotationsReturnsNoFields() {
        expect.that(new Expectations() {
            {
                one(checker).isAnnotated(with(any(AnnotatedElement.class)), with(same(Dummy.class)), with(equal(IGNORE)));
                will(returnValue(false));
            }
        });
        final Collection<Field> fields = locator.locate(WithoutRuntimeAnnotations.class, Dummy.class);
        expect.that(fields).isEmpty();
    }

    public void testLocateOnAClassWithAnnotationsReturnsFields() {
        final Field field1 = getField("string1");
        final Field field2 = getField("string2");
        final Field field3 = getField("string3");
        final Field field4 = getField("string4");
        final Field field5 = getField("string5");
        expect.that(new Expectations() {
            {
                one(checker).isAnnotated(field1, Dummy.class, IGNORE);
                will(returnValue(true));
                one(checker).isAnnotated(field2, Dummy.class, IGNORE);
                will(returnValue(true));
                one(checker).isAnnotated(field3, Dummy.class, IGNORE);
                will(returnValue(true));
                one(checker).isAnnotated(field4, Dummy.class, IGNORE);
                will(returnValue(true));
                one(checker).isAnnotated(field5, Dummy.class, IGNORE);
                will(returnValue(false));
            }
        });
        final Iterable<Field> fields = locator.locate(WithRuntimeAnnotations.class, Dummy.class);
        expect.that(fields).isNotNull();
        expect.that(fields).containsItem(field1);
        expect.that(fields).containsItem(field2);
        expect.that(fields).containsItem(field3);
        expect.that(fields).containsItem(field4);
    }

    private Field getField(final String fieldName) {
        return getFieldByName(WithRuntimeAnnotations.class, fieldName);
    }
}
