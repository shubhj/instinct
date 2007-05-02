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
import au.net.netstorm.boost.primordial.Primordial;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
public final class SpecificationContextImpl extends Primordial implements SpecificationContext {
    private final Class<?> contextClass;
    private final Method[] beforeSpecificationMethods;
    private final Method[] afterSpecificationMethods;
    private final Method specificationMethod;

    @Suggest({"All methods bar spec need to go into another object so that they can be resued.",
            "This class then takes that object."})
    public SpecificationContextImpl(final Class<?> contextClass, final Method[] beforeSpecificationMethods,
            final Method[] afterSpecificationMethods, final Method specificationMethod) {
        checkNotNull(contextClass, beforeSpecificationMethods, afterSpecificationMethods, specificationMethod);
        this.contextClass = contextClass;
        this.beforeSpecificationMethods = beforeSpecificationMethods;
        this.afterSpecificationMethods = afterSpecificationMethods;
        this.specificationMethod = specificationMethod;
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    public Method[] getBeforeSpecificationMethods() {
        return beforeSpecificationMethods;
    }

    public Method[] getAfterSpecificationMethods() {
        return afterSpecificationMethods;
    }

    public Method getSpecificationMethod() {
        return specificationMethod;
    }
}
