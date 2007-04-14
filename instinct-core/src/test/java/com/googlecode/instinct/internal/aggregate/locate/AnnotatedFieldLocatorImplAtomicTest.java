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

package com.googlecode.instinct.internal.aggregate.locate;

import java.lang.reflect.Field;
import com.googlecode.instinct.marker.annotate.Dummy;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.reflect.Reflector;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;
import org.junit.Assert;

public final class AnnotatedFieldLocatorImplAtomicTest extends InstinctTestCase {
    private AnnotatedFieldLocator locator;
    private AnnotationChecker checker;

    public void testConformsToClassTraits() {
        checkClass(AnnotatedFieldLocatorImpl.class, AnnotatedFieldLocator.class);
    }

    public void testLocateOnAClassWithNoAnnotationsReturnsNoFields() {
        expects(checker).method("isAnnotated").withAnyArguments().will(returnValue(false));
        final Field[] fields = locator.locate(WithoutRuntimeAnnotations.class, Dummy.class);
        assertNotNull(fields);
        Assert.assertArrayEquals(new Field[]{}, fields);
    }

    public void testLocateOnAClassWithAnnotationsReturnsFields() {
        final Field field1 = getField("string1");
        final Field field2 = getField("string2");
        final Field field3 = getField("string3");
        final Field field4 = getField("string4");
        final Field field5 = getField("string5");
        expects(checker).method("isAnnotated").with(eq(field1), same(Dummy.class)).will(returnValue(true));
        expects(checker).method("isAnnotated").with(eq(field2), same(Dummy.class)).will(returnValue(true));
        expects(checker).method("isAnnotated").with(eq(field3), same(Dummy.class)).will(returnValue(true));
        expects(checker).method("isAnnotated").with(eq(field4), same(Dummy.class)).will(returnValue(true));
        expects(checker).method("isAnnotated").with(eq(field5), same(Dummy.class)).will(returnValue(false));
        final Field[] fields = locator.locate(WithRuntimeAnnotations.class, Dummy.class);
        assertNotNull(fields);
        assertEquals(4, fields.length);
        assertEquals(field1, fields[0]);
        assertEquals(field2, fields[1]);
        assertEquals(field3, fields[2]);
        assertEquals(field4, fields[3]);
    }

    private Field getField(final String fieldName) {
        return Reflector.getFieldByName(WithRuntimeAnnotations.class, fieldName);
    }

    @Override
    public void setUpTestDoubles() {
        checker = mock(AnnotationChecker.class);
    }

    @Override
    public void setUpSubject() {
        locator = new AnnotatedFieldLocatorImpl();
        insertFieldValue(locator, "annotationChecker", checker);
    }
}
