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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.LifecycleMethodImpl;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.core.SpecificationMethodImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class RunnableItemBuilderImpl implements RunnableItemBuilder {
    private ClassInstantiator classInstantiator = new ClassInstantiatorImpl();
    private ClassEdge classEdge = new ClassEdgeImpl();

    public Collection<RunnableItem> build(final String itemsToRun) {
        checkNotNull(itemsToRun);
        final List<RunnableItem> builtItems = new ArrayList<RunnableItem>();
        buildItem(builtItems, itemsToRun);
        return builtItems;
    }

    private void buildItem(final List<RunnableItem> builtItems, final String itemsToRun) {
        if (itemsToRun.contains(METHOD_SEPARATOR)) {
            builtItems.add(buildSpecificationMethod(itemsToRun));
        } else {
            builtItems.add(buildContextClass(itemsToRun));
        }
    }

    private RunnableItem buildContextClass(final String contextToRun) {
        final Class<?> contextClass = classInstantiator.instantiateClass(contextToRun);
        return new ContextClassImpl(contextClass);
    }

    private RunnableItem buildSpecificationMethod(final String specToRun) {
        final String[] items = specToRun.split(METHOD_SEPARATOR);
        checkOnlyOneSpecMethod(items);
        final ContextClass contextClass = (ContextClass) buildContextClass(items[0]);
        final Method specificationMethod = classEdge.getMethod(contextClass.getType(), items[1]);
        return createSpecificationMethod(contextClass, specificationMethod);
    }

    private SpecificationMethod createSpecificationMethod(final ContextClass contextClass, final Method specMethod) {
        return new SpecificationMethodImpl(
                new LifecycleMethodImpl(specMethod), contextClass.getBeforeSpecificationMethods(), contextClass.getAfterSpecificationMethods());
    }

    private void checkOnlyOneSpecMethod(final String[] items) {
        if (items.length != 2) {
            throw new IllegalArgumentException("Specifications to run cannot contain more than one " + METHOD_SEPARATOR);
        }
    }
}
