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

package com.googlecode.instinct.internal.core;

import com.googlecode.instinct.internal.lang.Primordial;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import static com.googlecode.instinct.internal.util.ListUtil.listToString;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.Effect;
import fj.data.List;
import static fj.data.List.nil;
import java.lang.reflect.Method;

@Suggest("Spec this out.")
public final class CompleteSpecificationMethod extends Primordial implements SpecificationMethod {
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private Method method;
    private List<LifecycleMethod> beforeSpecificationMethods;
    private List<LifecycleMethod> afterSpecificationMethods;
    private List<SpecificationListener> specificationListeners = nil();
    private final Class<?> contextType;

    public <T> CompleteSpecificationMethod(final Class<T> contextType, final Method method, final List<LifecycleMethod> beforeSpecificationMethods,
            final List<LifecycleMethod> afterSpecificationMethods) {
        checkNotNull(contextType, method, beforeSpecificationMethods, afterSpecificationMethods);
        this.contextType = contextType;
        this.method = method;
        this.beforeSpecificationMethods = beforeSpecificationMethods;
        this.afterSpecificationMethods = afterSpecificationMethods;
    }

    public List<LifecycleMethod> getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public List<LifecycleMethod> getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }

    public String getName() {
        return method.getName();
    }

    public Method getMethod() {
        return method;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getContextClass() {
        return (Class<T>) contextType;
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
        specificationListeners = specificationListeners.cons(specificationListener);
    }

    public SpecificationResult run() {
        notifyListenersOfPreSpecification(this);
        final SpecificationResult result = specificationRunner.run(this);
        notifyListenersOfPostSpecification(this, result);
        return result;
    }

    @Override
    public String toString() {
        return CompleteSpecificationMethod.class.getSimpleName() + "[method=" + method + ";before=" + listToString(beforeSpecificationMethods)
                + ";after=" + listToString(afterSpecificationMethods) + "]";
    }

    private void notifyListenersOfPreSpecification(final SpecificationMethod specificationMethod) {
        specificationListeners.foreach(new Effect<SpecificationListener>() {
            public void e(final SpecificationListener listener) {
                listener.preSpecificationMethod(specificationMethod);
            }
        });
    }

    private void notifyListenersOfPostSpecification(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        specificationListeners.foreach(new Effect<SpecificationListener>() {
            public void e(final SpecificationListener listener) {
                listener.postSpecificationMethod(specificationMethod, specificationResult);
            }
        });
    }
}
