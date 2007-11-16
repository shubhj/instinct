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

package com.googlecode.instinct.internal.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;

@SuppressWarnings({"serial", "ClassExtendsConcreteCollection", "AnonymousInnerClassWithTooManyMethods"})
public final class ContextClassAggregatorImplSlowTest extends InstinctTestCase {
    private static final Class<?> CLASS_IN_SPEC_TREE = ContextClassAggregatorImplSlowTest.class;
    @Subject(auto = false) private ContextFinder finder;

    @Override
    public void setUpSubject() {
        finder = new ContextFinderImpl(CLASS_IN_SPEC_TREE);
    }

    @Fix("Breadcrumb: COme back and fix this.")
    public void nsoTestFindsCorrectContextClasses() {
        final JavaClassName[] classNames = finder.getContextNames("_custom_specification_group_");
        expect.that(classNames).hasSize(1);
        expect.that(classNames).containsItem(new JavaClassName() {
            public String getFullyQualifiedName() {
                return CustomSpecificationGroup.class.getName();
            }
        });
    }

    public void testNothing() {        
    }

    private static final class CustomSpecificationGroup {
        @Specification(groups = "_custom_specification_group_")
        public void customSpecGroup() {
        }
    }
}