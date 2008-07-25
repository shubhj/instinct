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

package com.googlecode.instinct.internal.locate.method;

import com.googlecode.instinct.internal.reflect.ConstructorInvoker;
import com.googlecode.instinct.internal.reflect.ConstructorInvokerImpl;
import static com.googlecode.instinct.internal.util.MethodEquality.methodEquals;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.naming.NamingConvention;
import fj.data.List;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public final class MarkedMethodLocatorImpl implements MarkedMethodLocator {
    private final AnnotatedMethodLocator annotatedMethodLocator = new AnnotatedMethodLocatorImpl();
    private final NamedMethodLocator namedMethodLocator = new NamedMethodLocatorImpl();
    private final ConstructorInvoker invoker = new ConstructorInvokerImpl();

    public <T> List<Method> locateAll(final Class<T> cls, final MarkingScheme markingScheme) {
        checkNotNull(cls, markingScheme);
        final MarkingScheme newScheme = getUserDefinedMarkingScheme(cls, markingScheme);
        return findMethodsByAnnotation(cls, newScheme).append(findMethodsByNamingConvention(cls, newScheme)).nub(methodEquals());
    }

    private <T> List<Method> findMethodsByAnnotation(final Class<T> cls, final MarkingScheme markingScheme) {
        return annotatedMethodLocator.locate(cls, markingScheme.getAnnotationType());
    }

    private <T> List<Method> findMethodsByNamingConvention(final Class<T> cls, final MarkingScheme markingScheme) {
        return namedMethodLocator.locate(cls, markingScheme.getNamingConvention());
    }

    private MarkingScheme getUserDefinedMarkingScheme(final AnnotatedElement cls, final MarkingScheme markingScheme) {
        if (cls.isAnnotationPresent(Context.class)) {
            final Class<? extends NamingConvention> namingConventionClass = cls.getAnnotation(Context.class).specificationNamingConvention();
            final NamingConvention namingConvention = (NamingConvention) invoker.invokeNullaryConstructor(namingConventionClass);
            return new MarkingSchemeImpl(markingScheme.getAnnotationType(), namingConvention, markingScheme.getAttributeConstraint());
        } else {
            return markingScheme;
        }
    }
}
