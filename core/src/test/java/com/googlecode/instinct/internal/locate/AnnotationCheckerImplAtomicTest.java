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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.AnnotationAttribute;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getDeclaredMethod;
import static com.googlecode.instinct.test.reflect.Reflector.getFieldByName;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;
import java.lang.reflect.Method;

@SuppressWarnings({"MethodReturnOfConcreteClass"})
public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    private AnnotationChecker annotationChecker;

    @Override
    public void setUpSubject() {
        annotationChecker = new AnnotationCheckerImpl();
    }

    public void testConformsToClassTraits() {
        checkClass(AnnotationCheckerImpl.class, AnnotationChecker.class);
    }

    public void testClassIsAnnotated() {
        expect.that(annotationChecker.isAnnotated(WithRuntimeAnnotations.class, Context.class, IGNORE)).isTrue();
        expect.that(annotationChecker.isAnnotated(WithoutRuntimeAnnotations.class, Context.class, IGNORE)).isFalse();
    }

    public void testMethodIsAnnotated() {
        expect.that(annotationChecker.isAnnotated(getMethod(WithRuntimeAnnotations.class, "toString"), Specification.class, IGNORE)).isTrue();
        expect.that(annotationChecker.isAnnotated(getMethod(WithoutRuntimeAnnotations.class, "toString"), Specification.class, IGNORE)).isFalse();
    }

    public void testFieldIsAnnotated() {
        expect.that(annotationChecker.isAnnotated(getFieldByName(WithRuntimeAnnotations.class, "string1"), Dummy.class, IGNORE)).isTrue();
        expect.that(annotationChecker.isAnnotated(getFieldByName(WithoutRuntimeAnnotations.class, "string1"), Dummy.class, IGNORE)).isFalse();
    }

    public void testFindsAnnotatedElementsWithSingleAttribute() {
        final Method annotatedMethod = getAnnotatedMethod("withSingleGroupAnnonation");
        final Object[] attributeValue = new String[]{"Single"};
        final boolean isAnnotated = annotationChecker.isAnnotated(annotatedMethod, Specification.class, groupAttribute("groups", attributeValue));
        expect.that(isAnnotated).isTrue();
    }

    public void testFindsAnnotatedElementsWithSingleAttributeAsArray() {
        final Method annotatedMethod = getAnnotatedMethod("withSingleGroupAnnonation");
        final Object[] attributeValue = new String[]{"Single"};
        final boolean isAnnotated = annotationChecker.isAnnotated(annotatedMethod, Specification.class, groupAttribute("groups", attributeValue));
        expect.that(isAnnotated).isTrue();
    }

    public void testDoesNotFindsAnnotatedElementsAttributesDoNotMatch() {
        final Method annotatedMethod = getAnnotatedMethod("withSingleGroupAnnonation");
        final Object[] attributeValue = new String[]{"No Match"};
        final boolean isAnnotated = annotationChecker.isAnnotated(annotatedMethod, Specification.class, groupAttribute("groups", attributeValue));
        expect.that(isAnnotated).isFalse();
    }

    public void testFindsAnnotatedElementsWithMultipleAttributes() {
        final Method annotatedMethod = getAnnotatedMethod("withMultipleGroupAnnonation");
        final Object[] attributeValue = new String[]{"Group 1", "Group 2"};
        final boolean isAnnotated = annotationChecker.isAnnotated(annotatedMethod, Specification.class, groupAttribute("groups", attributeValue));
        expect.that(isAnnotated).isTrue();
    }

    public void testFindsAnnotatedElementsWithEmptyAttributes() {
        final Method annotatedMethod = getAnnotatedMethod("withEmptyGroupAnnonation");
        final Object[] attributeValue = new String[]{};
        final boolean isAnnotated = annotationChecker.isAnnotated(annotatedMethod, Specification.class,
                groupAttribute("groups", attributeValue));
        expect.that(isAnnotated).isTrue();
    }

    public void testFindsNonArrayAnnotatedElements() {
        final Method annotatedMethod = getAnnotatedMethod("withNonArrayAnnotation");
        final boolean isAnnotated = annotationChecker.isAnnotated(annotatedMethod, Specification.class, groupAttribute("state", PENDING));
        expect.that(isAnnotated).isTrue();
    }

    private Method getAnnotatedMethod(final String methodName) {
        return getDeclaredMethod(WithGroupAnnotationOnSpecifications.class, methodName);
    }

    private AnnotationAttribute groupAttribute(final String attributeName, final Object attributeValue) {
        return new AnnotationAttribute(attributeName, attributeValue);
    }

    private static final class WithGroupAnnotationOnSpecifications {
        @Specification(groups = "Single")
        public void withSingleGroupAnnonation() {
        }

        @Specification(groups = {})
        public void withEmptyGroupAnnonation() {
        }

        @Specification(groups = {"Group 1", "Group 2"})
        public void withMultipleGroupAnnonation() {
        }

        @Specification(state = PENDING)
        public void withNonArrayAnnotation() {
        }
    }
}
