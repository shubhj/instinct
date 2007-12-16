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

package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.locate.MarkedMethodLocatorImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.lang.Primordial;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"OverlyCoupledClass"})
@Suggest("The interface should be typed?")
public final class ContextClassImpl extends Primordial implements ContextClass {
    private ContextRunner contextRunner = new StandardContextRunner();
    private MarkedMethodLocator methodLocator = new MarkedMethodLocatorImpl();
    private final Class<?> contextType;

    public <T> ContextClassImpl(final Class<T> contextType) {
        checkNotNull(contextType);
        this.contextType = contextType;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getType() {
        return (Class<T>) contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
        contextRunner.addContextListener(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        contextRunner.addSpecificationListener(specificationListener);
    }

    public ContextResult run() {
        return contextRunner.run(this);
    }

    public Collection<LifecycleMethod> getSpecificationMethods() {
        return findMethods(new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention(), IGNORE));
    }

    public Collection<LifecycleMethod> getBeforeSpecificationMethods() {
        return findMethods(new MarkingSchemeImpl(BeforeSpecification.class, new BeforeSpecificationNamingConvention(), IGNORE));
    }

    public Collection<LifecycleMethod> getAfterSpecificationMethods() {
        return findMethods(new MarkingSchemeImpl(AfterSpecification.class, new AfterSpecificationNamingConvention(), IGNORE));
    }

    @Suggest({"This logic is duplicated in StandardContextRunner.createSpecificationMethod()"})
    public Collection<SpecificationMethod> buildSpecificationMethods() {
        final Collection<SpecificationMethod> specificationMethodResults = new ArrayList<SpecificationMethod>();
        for (final LifecycleMethod lifecycleSpecificationMethod : getSpecificationMethods()) {
            specificationMethodResults.add(new SpecificationMethodImpl(lifecycleSpecificationMethod,
                    getBeforeSpecificationMethods(), getAfterSpecificationMethods()));
        }
        return specificationMethodResults;
    }

    private Collection<LifecycleMethod> findMethods(final MarkingScheme markingScheme) {
        final Set<LifecycleMethod> lifecycleMethodSet = new HashSet<LifecycleMethod>();
        final Collection<Method> methods = methodLocator.locateAll(contextType, markingScheme);
        for (final Method method : methods) {
            lifecycleMethodSet.add(new LifecycleMethodImpl(method, contextType));
        }
        return lifecycleMethodSet;
    }
}
