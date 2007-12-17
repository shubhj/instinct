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

package com.googlecode.instinct.internal.locate.method;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.locate.WithRuntimeAnnotations;
import com.googlecode.instinct.internal.locate.WithoutRuntimeAnnotations;
import com.googlecode.instinct.internal.locate.field.AnnotatedMethodLocator;
import com.googlecode.instinct.internal.locate.field.AnnotatedMethodLocatorImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.reflect.Method;
import java.util.Collection;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public final class AnAnnotatedMethodLocatorContext {
    private AnnotatedMethodLocator locator = new AnnotatedMethodLocatorImpl();

    @Specification
    public void shouldBeAnAnnotatedMethodLocator() {
        checkClass(AnnotatedMethodLocatorImpl.class, AnnotatedMethodLocator.class);
    }

    @Specification
    public void shouldNotReturnMethodsThatDoNotMatchTheSuppliedCriteria() {
        final Collection<Method> methods = locator.locate(WithoutRuntimeAnnotations.class, Specification.class);
        expect.that(methods).isOfSize(0);
    }

    @Specification
    public void shouldReturnMethodsThatMatchTheSuppliedCriteria() {
        final Collection<Method> methods = locator.locate(WithRuntimeAnnotations.class, Specification.class);
        expect.that(methods).isOfSize(2);
    }
}