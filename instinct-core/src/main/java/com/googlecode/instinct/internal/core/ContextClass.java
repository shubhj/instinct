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
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.runner.SpecificationListener;
import com.googlecode.instinct.runner.ContextListener;

public interface ContextClass extends RunnableItem {
    <T> Class<T> getType();

    String getName();

    void addContextListener(ContextListener contextListener);

    void addSpecificationListener(SpecificationListener specificationListener);

    ContextResult run();

    Collection<LifecycleMethod> getSpecificationMethods();

    Collection<LifecycleMethod> getBeforeSpecificationMethods();

    Collection<LifecycleMethod> getAfterSpecificationMethods();
}
