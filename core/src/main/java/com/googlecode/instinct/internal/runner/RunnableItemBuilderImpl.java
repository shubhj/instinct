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

import com.googlecode.instinct.internal.core.AbstractContextClass;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import static com.googlecode.instinct.internal.util.Fj.toFjList;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ClassInstantiator;
import com.googlecode.instinct.internal.util.instance.ClassInstantiatorImpl;
import fj.F;
import fj.data.List;
import static java.lang.reflect.Modifier.isAbstract;

public final class RunnableItemBuilderImpl implements RunnableItemBuilder {
    private ClassInstantiator classInstantiator = new ClassInstantiatorImpl();

    public List<RunnableItem> build(final String itemsToRun) {
        checkNotNull(itemsToRun);
        return toFjList(itemsToRun.split(ITEM_SEPARATOR)).map(new F<String, RunnableItem>() {
            public RunnableItem f(final String item) {
                return buildItem(item);
            }
        });
    }

    private RunnableItem buildItem(final String itemToRun) {
        if (itemToRun.contains(METHOD_SEPARATOR)) {
            return buildSpecificationMethod(itemToRun);
        } else {
            return buildContextClass(itemToRun);
        }
    }

    private RunnableItem buildSpecificationMethod(final String specToRun) {
        final String[] items = specToRun.split(METHOD_SEPARATOR);
        checkOnlyOneSpecMethod(items);
        final ContextClass contextClass = buildContextClass(items[0]);
        final List<SpecificationMethod> specs = contextClass.getSpecificationMethods().filter(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod spec) {
                return spec.getName().equals(items[1]);
            }
        });
        if (specs.isEmpty()) {
            throw new IllegalArgumentException(
                    "Specification method '" + contextClass.getType().getName() + METHOD_SEPARATOR + items[1] + "' does not exist");
        } else {
            return specs.head();
        }
    }

    private ContextClass buildContextClass(final String contextToRun) {
        final Class<?> contextClass = classInstantiator.instantiateClass(contextToRun);
        if (isAbstract(contextClass.getModifiers())) {
            return new AbstractContextClass(contextClass);
        } else {
            return new ContextClassImpl(contextClass);
        }
    }

    private void checkOnlyOneSpecMethod(final String[] items) {
        if (items.length != 2) {
            throw new IllegalArgumentException("Specification to run cannot contain more than one " + METHOD_SEPARATOR);
        }
    }
}
