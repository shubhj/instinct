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

package com.googlecode.instinct.internal.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;

@Suggest({"Make a runner that runs all contexts in a class (embedded anon inner)"})
public final class StandardContextRunner implements ContextRunner {
    private final Collection<ContextListener> contextListeners = new ArrayList<ContextListener>();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
        contextListeners.add(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationRunner.addSpecificationListener(specificationListener);
    }

    public ContextResult run(final ContextClass contextClass) {
        checkNotNull(contextClass);
        // notify listeners
        final ContextResult contextResult = doRun(contextClass);
        // notify listeners
        return contextResult;
    }

    @Suggest("Breadcrumb - Use specification method to run each spec. Them remove the spec runner.")
    private ContextResult doRun(final ContextClass contextClass) {
        final ContextResult contextResult = new ContextResultImpl(contextClass.getName());
        final Method[] specificationMethods = toMethodArray(contextClass.getSpecificationMethods());
        final Method[] beforeSpecificationMethods = toMethodArray(contextClass.getBeforeSpecificationMethods());
        final Method[] afterSpecificationMethods = toMethodArray(contextClass.getAfterSpecificationMethods());
        for (final Method specificationMethod : specificationMethods) {
            final SpecificationContext specificationContext = new SpecificationContextImpl(
                    contextClass.getType(), beforeSpecificationMethods, afterSpecificationMethods, specificationMethod);
            final SpecificationResult specificationResult = specificationRunner.run(specificationContext);
            contextResult.addSpecificationResult(specificationResult);
        }
        return contextResult;
    }

    private Method[] toMethodArray(final Collection<LifecycleMethod> lifecycleMethods) {
        final Method[] methods = new Method[lifecycleMethods.size()];
        int i = 0;
        for (final LifecycleMethod lifecycleMethod : lifecycleMethods) {
            methods[i] = lifecycleMethod.getMethod();
            i++;
        }
        return methods;
    }
}
