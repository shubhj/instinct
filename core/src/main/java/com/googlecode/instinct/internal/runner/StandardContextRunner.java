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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.Effect;
import fj.data.List;
import static fj.data.List.nil;

public final class StandardContextRunner implements ContextRunner {
    private List<ContextListener> contextListeners = nil();
    private List<SpecificationListener> specificationListeners = nil();

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
        contextListeners = contextListeners.cons(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners = specificationListeners.cons(specificationListener);
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
        final List<SpecificationMethod> specs = contextClass.getSpecificationMethods();
        specs.foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod spec) {
                addSpecificationListeners(spec);
                contextResult.addSpecificationResult(spec.run());
            }
        });
    }

    private void addSpecificationListeners(final RunnableItem specificationMethod) {
        specificationListeners.foreach(new Effect<SpecificationListener>() {
            public void e(final SpecificationListener listener) {
                specificationMethod.addSpecificationListener(listener);
            }
        });
    }

    private void notifyListenersOfPreContextRun(final ContextClass contextClass) {
        contextListeners.foreach(new Effect<ContextListener>() {
            public void e(final ContextListener listener) {
                listener.preContextRun(contextClass);
            }
        });
    }

    private void notifyListenersOfPostContextRun(final ContextClass contextClass, final ContextResult contextResult) {
        contextListeners.foreach(new Effect<ContextListener>() {
            public void e(final ContextListener listener) {
                listener.postContextRun(contextClass, contextResult);
            }
        });
    }
}
