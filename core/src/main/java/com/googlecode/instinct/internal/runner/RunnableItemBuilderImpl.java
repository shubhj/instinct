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
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethodImpl;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethodImpl;
import com.googlecode.instinct.internal.core.AbstractContextClass;
import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.isAbstract;
import java.util.ArrayList;
import java.util.Collection;

public final class RunnableItemBuilderImpl implements RunnableItemBuilder {
    private ClassInstantiator classInstantiator = new ClassInstantiatorImpl();
    private ClassEdge classEdge = new ClassEdgeImpl();

    public Collection<RunnableItem> build(final String itemsToRun) {
        checkNotNull(itemsToRun);
        final Collection<RunnableItem> builtItems = new ArrayList<RunnableItem>();
        buildItems(itemsToRun, builtItems);
        return builtItems;
    }

    private void buildItems(final String itemsToRun, final Collection<RunnableItem> builtItems) {
        final String[] items = itemsToRun.split(ITEM_SEPARATOR);
        for (final String item : items) {
            builtItems.add(buildItem(item));
        }
    }

    private RunnableItem buildItem(final String itemToRun) {
        if (itemToRun.contains(METHOD_SEPARATOR)) {
            return buildSpecificationMethod(itemToRun);
        } else {
            return buildContextClass(itemToRun);
        }
    }

    private RunnableItem buildContextClass(final String contextToRun) {
        final Class<?> contextClass = classInstantiator.instantiateClass(contextToRun);
        if (isAbstract(contextClass.getModifiers())) {
            return new AbstractContextClass(contextClass);
        } else {
            return new ContextClassImpl(contextClass);
        }
    }

    private RunnableItem buildSpecificationMethod(final String specToRun) {
        final String[] items = specToRun.split(METHOD_SEPARATOR);
        checkOnlyOneSpecMethod(items);
        final ContextClass contextClass = (ContextClass) buildContextClass(items[0]);
        final Method specificationMethod = findSpecMethod(contextClass, items[1]);
        return createSpecificationMethod(contextClass, specificationMethod);
    }

    private Method findSpecMethod(final ContextClass contextClass, final String methodName) {
        try {
            return classEdge.getDeclaredMethod(contextClass.getType(), methodName);
        } catch (EdgeException e) {
            throw new IllegalArgumentException(
                    "Specification method '" + contextClass.getType().getName() + METHOD_SEPARATOR + methodName + "' does not exist", e);
        }
    }

    private RunnableItem createSpecificationMethod(final ContextClass contextClass, final Method specMethod) {
        return new SpecificationMethodImpl(
                new LifecycleMethodImpl(specMethod), contextClass.getBeforeSpecificationMethods(), contextClass.getAfterSpecificationMethods());
    }

    private void checkOnlyOneSpecMethod(final String[] items) {
        if (items.length != 2) {
            throw new IllegalArgumentException("Specification to run cannot contain more than one " + METHOD_SEPARATOR);
        }
    }
}
