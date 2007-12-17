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

import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.AnnotationAttribute;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings({"MethodParameterOfConcreteClass"})
public final class AnnotationCheckerImpl implements AnnotationChecker {
    private final ClassEdge classEdge = new ClassEdgeImpl();

    public <A extends Annotation> boolean isAnnotated(final AnnotatedElement annotatedElement, final Class<A> annotationType,
            final AnnotationAttribute attributeConstraint) {
        checkNotNull(annotatedElement, annotationType, attributeConstraint);
        final boolean annotationPresent = annotatedElement.isAnnotationPresent(annotationType);
        return annotationPresent && annotationValueMatchesRequestedValue(annotatedElement, annotationType, attributeConstraint);
    }

    private <A extends Annotation> boolean annotationValueMatchesRequestedValue(final AnnotatedElement annotatedElement,
            final Class<A> annotationType, final AnnotationAttribute attributeConstraint) {
        if (attributeConstraint.equals(IGNORE)) {
            return true;
        } else {
            final Object expectedAnnotationValue = attributeConstraint.getAttributeValue();
            final Object actualAnnotationValue = getActualAttributeValue(annotationType, attributeConstraint, annotatedElement);
            if (actualAnnotationValue.getClass().isArray() && expectedAnnotationValue.getClass().isArray()) {
                return Arrays.equals((Object[]) actualAnnotationValue, (Object[]) expectedAnnotationValue);
            } else {
                return actualAnnotationValue.equals(expectedAnnotationValue);
            }
        }
    }

    private <A extends Annotation> Object getActualAttributeValue(final Class<A> annotationType, final AnnotationAttribute annotationAttribute,
            final AnnotatedElement attributeConstraint) {
        final Method attributeMethod = classEdge.getDeclaredMethod(annotationType, annotationAttribute.getAttributeName());
        return new MethodEdgeImpl(attributeMethod).invoke(attributeConstraint.getAnnotation(annotationType));
    }
}
