/*
 * Copyright 2006-2008 Tom Adams, Workingmouse
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

import com.googlecode.instinct.internal.lang.Primordial;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.data.List;

public final class ContextClassImpl extends Primordial implements ContextClass {
    private ContextRunner contextRunner = new StandardContextRunner();
    private final SpecificationMethodBuilder specificationBuilder = new SpecificationMethodBuilderImpl();
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

    public List<SpecificationMethod> getSpecificationMethods() {
        return specificationBuilder.buildSpecificationMethods(contextType);
    }

    public List<LifecycleMethod> getBeforeSpecificationMethods() {
        return specificationBuilder.buildBeforeSpecificationMethods(contextType);
    }

    public List<LifecycleMethod> getAfterSpecificationMethods() {
        return specificationBuilder.buildAfterSpecificationMethods(contextType);
    }
}
