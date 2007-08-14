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

package com.googlecode.instinct.integrate.junit4;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.ContextClasses;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SpecificationBuilderSpike {
    public final Set<SpecificationMethod> createSpecifications(final Class<?>[] annotatedClasses) {
        final Set<SpecificationMethod> methods = new HashSet<SpecificationMethod>();
        for (final Class<?> annotatedClass : annotatedClasses) {
            final ContextClass contextClass = new ContextClassImpl(annotatedClass);
            final Collection<SpecificationMethod> specificationMethods = contextClass.buildSpecificationMethods();
            methods.addAll(specificationMethods);
        }
        return methods;
    }

    @Suggest({"What if annotation not present?", "What if annotation is empty?"})
    private static Class<?>[] getAnnotatedClasses(final Class<?> cls) {
        final ContextClasses specificationClass = cls.getAnnotation(ContextClasses.class);
        return specificationClass.value();
    }
}
