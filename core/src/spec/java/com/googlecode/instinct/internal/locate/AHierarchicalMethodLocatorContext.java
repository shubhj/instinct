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

package com.googlecode.instinct.internal.locate;

import com.googlecode.instinct.defect.defect8.data.AContext;
import com.googlecode.instinct.defect.defect8.data.SuperContext;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.expect.ExpectAtomicTest;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import java.lang.reflect.Method;
import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AHierarchicalMethodLocatorContext {

    @Subject private final HierarchicalMethodLocator locator = new HierarchicalMethodLocatorImpl();

    public void shouldRetunAllMethodsForAGivenClass() {
        final Set<Method> returnedMethods = locator.locate(AContext.class);
        final Set<Method> expectedMethods = getExpectedMethodsForAContext();

        expect.that(returnedMethods).hasSize(expectedMethods.size());
        expect.that(returnedMethods).containsItems(expectedMethods);
    }

    @Specification
    public void shouldReturnAllMethodsForAnotherClass() {
        final Set<Method> returnedMethods = locator.locate(ExpectAtomicTest.class);
        final Set<Method> expectedMethods = getExpectedMethodsForExceptAtomicTest();

        expect.that(returnedMethods).hasSize(expectedMethods.size());
        expect.that(returnedMethods).containsItems(expectedMethods);
    }

    public Set<Method> getExpectedMethodsForAContext() {
        final Set<Method> methods = new HashSet<Method>();
        methods.addAll(getDeclaredMethods(AContext.class));
        methods.addAll(getDeclaredMethods(SuperContext.class));
        methods.addAll(getDeclaredMethods(Object.class));
        return methods;
    }

    public Set<Method> getExpectedMethodsForExceptAtomicTest() {
        final Set<Method> methods = new HashSet<Method>();
        methods.addAll(getDeclaredMethods(ExpectAtomicTest.class));
        methods.addAll(getDeclaredMethods(InstinctTestCase.class));
        methods.addAll(getDeclaredMethods(TestCase.class));
        methods.addAll(getDeclaredMethods(Assert.class));
        methods.addAll(getDeclaredMethods(Object.class));
        return methods;
    }

    private List<Method> getDeclaredMethods(final Class<?> cls) {
        return asList(cls.getDeclaredMethods());
    }
}