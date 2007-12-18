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

import com.googlecode.instinct.internal.locate.AnnotationChecker;
import com.googlecode.instinct.internal.locate.AnnotationCheckerImpl;
import com.googlecode.instinct.internal.locate.method.HierarchicalMethodLocator;
import com.googlecode.instinct.internal.locate.method.HierarchicalMethodLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class AnnotatedMethodLocatorImpl implements AnnotatedMethodLocator {
    private final AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
    private final HierarchicalMethodLocator methodLocator = new HierarchicalMethodLocatorImpl();

    public <A extends Annotation, T> Collection<Method> locate(final Class<T> cls, final Class<A> runtimeAnnotationType) {
        checkNotNull(cls, runtimeAnnotationType);
        final Set<Method> annotatedMethods = new HashSet<Method>();
        for (final Method method : findMethods(cls)) {
            if (methodIsAnnotated(runtimeAnnotationType, method)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private Set<Method> findMethods(final Class<?> cls) {
        return methodLocator.locate(cls);
    }

    private <A extends Annotation, T> boolean methodIsAnnotated(final Class<A> runtimeAnnotationType, final Method method) {
        return annotationChecker.isAnnotated(method, runtimeAnnotationType, IGNORE);
    }
}
