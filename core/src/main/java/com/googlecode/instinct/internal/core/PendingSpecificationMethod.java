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
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationResultImpl;
import com.googlecode.instinct.internal.runner.SpecificationRunPendingStatus;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.NO_REASON;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.data.List;
import java.lang.reflect.Method;

/** A specification that is not pending completion. Pens specifications will not be run. */
public final class PendingSpecificationMethod extends Primordial implements SpecificationMethod {
    private final Method method;
    private List<LifecycleMethod> beforeSpecificationMethods;
    private List<LifecycleMethod> afterSpecificationMethods;

    public PendingSpecificationMethod(final Method method, final List<LifecycleMethod> beforeSpecificationMethods,
            final List<LifecycleMethod> afterSpecificationMethods) {
        checkNotNull(method, beforeSpecificationMethods, afterSpecificationMethods);
        this.method = method;
        this.beforeSpecificationMethods = beforeSpecificationMethods;
        this.afterSpecificationMethods = afterSpecificationMethods;
    }

    public String getPendingReason() {
        return method.getAnnotation(Specification.class).reason();
    }

    public List<LifecycleMethod> getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public List<LifecycleMethod> getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }

    public String getName() {
        return method.getName() + " [PENDING" + (getPendingReason().equals(NO_REASON) ? "" : " - " + getPendingReason()) + "]";
    }

    public Method getMethod() {
        return method;
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getContextClass() {
        return (Class<T>) method.getDeclaringClass();
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
    }

    public SpecificationResult run() {
        return new SpecificationResultImpl(getName(), new SpecificationRunPendingStatus(), 0L);
    }

    @Override
    public String toString() {
        return PendingSpecificationMethod.class.getSimpleName() + "[method=" + method + ";before=" + beforeSpecificationMethods.toCollection() +
                ";after=" + afterSpecificationMethods.toCollection() + "]";
    }
}
