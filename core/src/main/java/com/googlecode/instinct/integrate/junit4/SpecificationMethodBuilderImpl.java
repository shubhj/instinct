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
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;
import com.googlecode.instinct.internal.util.param.ParamChecker;
import java.util.Collection;
import java.util.HashSet;

public final class SpecificationMethodBuilderImpl implements SpecificationMethodBuilder {
    private ContextClassesFinder finder = new ContextClassesFinderImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();

    public <T> Collection<SpecificationMethod> build(final Class<T> cls) {
        ParamChecker.checkNotNull(cls);
        final Collection<SpecificationMethod> specificationMethods = new HashSet<SpecificationMethod>();
        final Collection<Class<?>> classes = finder.getContextClasses(cls);
        for (final Class<?> classWithContext : classes) {
            final ContextClass contextClass = objectFactory.create(ContextClassImpl.class, classWithContext);
            specificationMethods.addAll(contextClass.buildSpecificationMethods());
        }
        //        final ContextClasses annotation = cls.getAnnotation(ContextClasses.class);
        //        final Class<?>[] classes = annotation.value();
        return specificationMethods;
    }
}
