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

package com.googlecode.instinct.runner;

import java.util.Collection;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.locate.ContextWithSpecsWithDifferentAccessModifiers;
import com.googlecode.instinct.internal.core.RunnableItem;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.METHOD_SEPARATOR;
import com.googlecode.instinct.internal.runner.RunnableItemBuilderImpl;
import com.googlecode.instinct.test.InstinctTestCase;

public final class RunnableItemBuilderImplSlowTest extends InstinctTestCase {
    private RunnableItemBuilder runnableItemBuilder;

    @Override
    public void setUpSubject() {
        runnableItemBuilder = new RunnableItemBuilderImpl();
    }

    public void testCanBuildSpecificationMethodFromNonPublicMethod() {
        checkBuildCorrectNumberOfItems(ContextWithSpecsWithDifferentAccessModifiers.class, "whoCares", 1);
        checkBuildCorrectNumberOfItems(ContextWithSpecsWithDifferentAccessModifiers.class, "notMe", 1);
        checkBuildCorrectNumberOfItems(ContextWithSpecsWithDifferentAccessModifiers.class, "norMe", 1);
        checkBuildCorrectNumberOfItems(ContextWithSpecsWithDifferentAccessModifiers.class, "iDo", 1);
        checkBuildCorrectNumberOfItems(ContextWithSpecsWithDifferentAccessModifiers.class, "orMe", 1);
    }

    private <T> void checkBuildCorrectNumberOfItems(final Class<T> contextClass, final String methodName, final int expectedRunnables) {
        final Collection<RunnableItem> items = runnableItemBuilder.build(contextClass.getName() + METHOD_SEPARATOR + methodName);
        expect.that(items).hasSize(expectedRunnables);
    }
}
