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

package com.googlecode.instinct.internal.locate;

import java.lang.reflect.Field;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import org.jmock.Expectations;

public final class MarkedFieldLocatorImplAtomicTest extends InstinctTestCase {
    @Subject private MarkedFieldLocator fieldLocator;
    @Mock private AnnotatedFieldLocator annotatedFieldLocator;
    @Mock private MarkingScheme markingScheme;
    @Dummy private Field[] annotatedFields;
    @Dummy private Dummy annotationType;

    @Override
    public void setUpSubject() {
        fieldLocator = createSubject(MarkedFieldLocatorImpl.class, annotatedFieldLocator);
    }

    public void testConformsToClassTraits() {
        checkClass(MarkedFieldLocatorImpl.class, MarkedFieldLocator.class);
    }

    public void testUsesAnnotatedLocator() {
        expect.that(new Expectations() {
            {
                one(markingScheme).getAnnotationType();
                will(returnValue(annotationType.getClass()));
                one(annotatedFieldLocator).locate(WithRuntimeAnnotations.class, annotationType.getClass());
                will(returnValue(annotatedFields));
            }
        });
        final Field[] fields = fieldLocator.locateAll(WithRuntimeAnnotations.class, markingScheme);
        expect.that(fields).equalTo(annotatedFields);
    }
}
