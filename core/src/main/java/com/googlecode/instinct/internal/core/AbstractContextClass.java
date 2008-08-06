/*
 * Copyright 2006-2007 Workingmouse
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

import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.data.List;
import static fj.data.List.nil;

public final class AbstractContextClass implements ContextClass {
    private Class<?> contextType;

    public <T> AbstractContextClass(final Class<T> contextType) {
        checkNotNull(contextType);
        this.contextType = contextType;
    }

    public void addContextListener(final ContextListener contextListener) {
        checkNotNull(contextListener);
    }

    public void addSpecificationListener(final SpecificationListener specificationListener) {
        checkNotNull(specificationListener);
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<T> getType() {
        return (Class<T>) contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }

    public ContextResult run() {
        return new AbstractContextContextResult(getName());
    }

    public fj.data.List<SpecificationMethod> getSpecificationMethods() {
        return nil();
    }

    public fj.data.List<LifecycleMethod> getBeforeSpecificationMethods() {
        return nil();
    }

    public fj.data.List<LifecycleMethod> getAfterSpecificationMethods() {
        return nil();
    }

    private static final class AbstractContextContextResult implements ContextResult {
        private final String contextName;

        private AbstractContextContextResult(final String contextName) {
            this.contextName = contextName;
        }

        public void addSpecificationResult(final SpecificationResult specificationResult) {
            checkNotNull(specificationResult);
        }

        public List<SpecificationResult> getSpecificationResults() {
            return nil();
        }

        public String getContextName() {
            return contextName;
        }

        public int getNumberOfSpecificationsRun() {
            return 0;
        }

        public int getNumberOfSuccesses() {
            return 0;
        }

        public int getNumberOfFailures() {
            return 0;
        }

        public boolean completedSuccessfully() {
            return true;
        }

        public long getExecutionTime() {
            return 0L;
        }
    }
}
