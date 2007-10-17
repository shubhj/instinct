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

import java.util.ArrayList;
import java.util.Collection;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.core.SpecificationMethodImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;

@Suggest({"Make a runner that runs all contexts in a class (embedded anon inner)"})
public final class StandardContextRunner implements ContextRunner {
    private final Collection<ContextListener> contextListeners = new ArrayList<ContextListener>();
    private final Collection<SpecificationListener> specificationListeners = new ArrayList<SpecificationListener>();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
        contextListeners.add(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners.add(specificationListener);
    }

    public ContextResult run(final ContextClass contextClass) {
        checkNotNull(contextClass);
        notifyListenersOfPreContextRun(contextClass);
        final ContextResult contextResult = runContextClass(contextClass);
        notifyListenersOfPostContextRun(contextClass, contextResult);
        return contextResult;
    }

    private ContextResult runContextClass(final ContextClass contextClass) {
        final ContextResult contextResult = new ContextResultImpl(contextClass.getName());
        runSpecifications(contextClass, contextResult);
        return contextResult;
    }

    private void runSpecifications(final ContextClass contextClass, final ContextResult contextResult) {
        for (final LifecycleMethod specificationMethod : contextClass.getSpecificationMethods()) {
            final SpecificationMethod spec = createSpecificationMethod(contextClass, specificationMethod);
            final SpecificationResult specificationResult = runSpecification(spec);
            contextResult.addSpecificationResult(specificationResult);
        }
    }

    private SpecificationResult runSpecification(final SpecificationMethod specificationMethod) {
        addSpecificationListeners(specificationMethod);
        return specificationMethod.run();
    }

    private SpecificationMethod createSpecificationMethod(final ContextClass contextClass, final LifecycleMethod specificationMethod) {
        final Collection<LifecycleMethod> beforeSpecificationMethods = contextClass.getBeforeSpecificationMethods();
        final Collection<LifecycleMethod> afterSpecificationMethods = contextClass.getAfterSpecificationMethods();
        return new SpecificationMethodImpl(specificationMethod, beforeSpecificationMethods, afterSpecificationMethods);
    }

    private void addSpecificationListeners(final SpecificationMethod specificationMethod) {
        for (final SpecificationListener specificationListener : specificationListeners) {
            specificationMethod.addSpecificationListener(specificationListener);
        }
    }

    private void notifyListenersOfPreContextRun(final ContextClass contextClass) {
        for (final ContextListener contextListener : contextListeners) {
            contextListener.preContextRun(contextClass);
        }
    }

    private void notifyListenersOfPostContextRun(final ContextClass contextClass, final ContextResult contextResult) {
        for (final ContextListener contextListener : contextListeners) {
            contextListener.postContextRun(contextClass, contextResult);
        }
    }
}
