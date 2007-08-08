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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.MarkingScheme;

public final class MarkedMethodLocatorImpl implements MarkedMethodLocator {
    private final AnnotatedMethodLocator annotatedMethodLocator = new AnnotatedMethodLocatorImpl();

    @Suggest("Return an unmodifiable collection.")
    public <T> Collection<Method> locateAll(final Class<T> cls, final MarkingScheme markingScheme) {
        final Method[] methods = findMethodsByAnnotation(cls, markingScheme.getAnnotationType());
        return new HashSet<Method>(Arrays.asList(methods));
    }

    private <A extends Annotation, T> Method[] findMethodsByAnnotation(final Class<T> cls, final Class<A> annotationType) {
        return annotatedMethodLocator.locate(cls, annotationType);
    }
}