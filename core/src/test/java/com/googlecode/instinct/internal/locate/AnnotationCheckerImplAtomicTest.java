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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getFieldByName;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(AnnotationCheckerImpl.class, AnnotationChecker.class);
    }

    public void testClassIsAnnotated() {
        checkIsAnnotated(WithRuntimeAnnotations.class, Context.class, true);
        checkIsAnnotated(WithoutRuntimeAnnotations.class, Context.class, false);
    }

    public void testMethodIsAnnotated() {
        checkIsAnnotated(getMethod(WithRuntimeAnnotations.class, "toString"), Specification.class, true);
        checkIsAnnotated(getMethod(WithoutRuntimeAnnotations.class, "toString"), Specification.class, false);
    }

    public void testFieldIsAnnotated() {
        checkIsAnnotated(getFieldByName(WithRuntimeAnnotations.class, "string1"), Dummy.class, true);
        checkIsAnnotated(getFieldByName(WithoutRuntimeAnnotations.class, "string1"), Dummy.class, false);
    }

    private <A extends Annotation> void checkIsAnnotated(final AnnotatedElement annotatedElement, final Class<A> expectedAnnotation,
            final boolean expectingAnnotation) {
        final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
        assertEquals(expectingAnnotation, annotationChecker.isAnnotated(annotatedElement, expectedAnnotation));
    }
}
