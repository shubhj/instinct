/*
 * Copyright 2006-2008 Workingmouse
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

import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import static com.googlecode.instinct.internal.util.Fj.toFjList;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;
import fj.F;
import fj.data.List;
import static fj.data.List.join;
import java.util.Collection;

public final class SpecificationMethodBuilderImpl implements SpecificationMethodBuilder {
    private ContextClassesFinder finder = new ContextClassesFinderImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();

    public <T> List<SpecificationMethod> buildSpecifications(final Class<T> cls) {
        checkNotNull(cls);
        final Collection<Class<?>> contextClasses = finder.getContextClasses(cls);
        final List<List<SpecificationMethod>> list = toFjList(contextClasses).map(new F<Class<?>, List<SpecificationMethod>>() {
            public List<SpecificationMethod> f(final Class<?> a) {
                return objectFactory.create(ContextClassImpl.class, a).getSpecificationMethods();
            }
        });
        return join(list);
    }
}
