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
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class MarkedFieldLocatorImplAtomicTest extends InstinctTestCase {
    private static final Class<WithRuntimeAnnotations> CLASS_WITH_ANNOTATIONS = WithRuntimeAnnotations.class;
    private MarkedFieldLocator fieldLocator;
    private AnnotatedFieldLocator annotatedFieldLocator;
    private Field[] annotatedFields;

    public void testConformsToClassTraits() {
        checkClass(MarkedFieldLocatorImpl.class, MarkedFieldLocator.class);
    }

    public void testUsesAnnotatedLocator() {
        expects(annotatedFieldLocator).method("locate").with(same(CLASS_WITH_ANNOTATIONS), same(Dummy.class)).will(returnValue(annotatedFields));
        final Field[] fields = fieldLocator.locateAll(CLASS_WITH_ANNOTATIONS, Dummy.class, new DummyNamingConvention());
        assertSame(annotatedFields, fields);
    }

    @Override
    public void setUpTestDoubles() {
        annotatedFieldLocator = mock(AnnotatedFieldLocator.class);
        annotatedFields = new Field[]{};
    }

    @Override
    public void setUpSubject() {
        fieldLocator = new MarkedFieldLocatorImpl();
        insertFieldValue(fieldLocator, "annotatedFieldLocator", annotatedFieldLocator);
    }
}
