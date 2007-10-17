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

import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Suggest;
import java.lang.annotation.Annotation;
import java.util.Collection;

@Suggest({"Breadcrumb - Add a getDeclaringClass() that returns ContextClass or raw class?"})
public interface SpecificationMethod extends RunnableItem {
    SpecificationResult run();

    boolean isPending();

    @Suggest("Something is misnamed here.  This returns a LifecycleMethod and not a SpecMethod, which is this class.")
    LifecycleMethod getSpecificationMethod();

    Collection<LifecycleMethod> getBeforeSpecificationMethods();

    Collection<LifecycleMethod> getAfterSpecificationMethods();

    String getName();

    Class<?> getDeclaringClass();

    Annotation[][] getParameterAnnotations();

    Class<? extends Throwable> getExpectedException();

    String getExpectedExceptionMessage();
}
