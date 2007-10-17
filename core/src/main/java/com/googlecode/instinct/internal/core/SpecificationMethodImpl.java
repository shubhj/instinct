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

import au.net.netstorm.boost.primordial.Primordial;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Specification.SpecificationState;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

public final class SpecificationMethodImpl extends Primordial implements SpecificationMethod {
    private SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private final LifecycleMethod specificationMethod;
    private Collection<LifecycleMethod> beforeSpecificationMethods;
    private Collection<LifecycleMethod> afterSpecificationMethods;

    public SpecificationMethodImpl(final LifecycleMethod specificationMethod, final Collection<LifecycleMethod> beforeSpecificationMethods,
            final Collection<LifecycleMethod> afterSpecificationMethods) {
        checkNotNull(specificationMethod, beforeSpecificationMethods, afterSpecificationMethods);
        this.specificationMethod = specificationMethod;
        this.beforeSpecificationMethods = beforeSpecificationMethods;
        this.afterSpecificationMethods = afterSpecificationMethods;
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationRunner.addSpecificationListener(specificationListener);
    }

    public SpecificationResult run() {
        return specificationRunner.run(this);
    }

    public boolean isPending() {
        final Method method = specificationMethod.getMethod();
        return method.isAnnotationPresent(Specification.class) && method.getAnnotation(Specification.class).state() == SpecificationState.PENDING;
    }

    public LifecycleMethod getSpecificationMethod() {
        return specificationMethod;
    }

    public Class<?> getDeclaringClass() {
        return specificationMethod.getDeclaringClass();
    }

    public Annotation[][] getParameterAnnotations() {
        return specificationMethod.getParameterAnnotations();
    }

    public Collection<LifecycleMethod> getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public Collection<LifecycleMethod> getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }

    public Class<? extends Throwable> getExpectedException() {
        final Method method = specificationMethod.getMethod();
        if (method.isAnnotationPresent(Specification.class)) {
            return method.getAnnotation(Specification.class).expectedException();
        } else {
            return Specification.NoExpectedException.class;
        }
    }

    public String getName() {
        return specificationMethod.getName();
    }
}
