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

import java.util.Collection;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.SpecificationListener;

public final class SpecificationMethodImpl implements SpecificationMethod {
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

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationRunner.addSpecificationListener(specificationListener);
    }

    public SpecificationResult run() {
        return specificationRunner.run(this);
    }

    public LifecycleMethod getSpecificationMethod() {
        return specificationMethod;
    }

    public Collection<LifecycleMethod> getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public Collection<LifecycleMethod> getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }
}
