/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.runner;

import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import java.lang.annotation.Annotation;

public final class ParameterAnnotationFinderImpl implements ParameterAnnotationFinder {
    public <A extends Annotation> boolean hasAnnotation(final Class<A> annotationClass, final Annotation[][] parameterAnnotations) {
        checkNotNull(annotationClass, parameterAnnotations);
        for (final Annotation[] annotations : parameterAnnotations) {
            if (scanForAnnotationInArray(annotationClass, annotations)) {
                return true;
            }
        }
        return false;
    }

    private <A extends Annotation> boolean scanForAnnotationInArray(final Class<A> annotationClass, final Annotation[] annotations) {
        if (annotations.length != 0) {
            for (final Annotation annotation : annotations) {
                if (annotation.annotationType() == annotationClass) {
                    return true;
                }
            }
        }
        return false;
    }
}
