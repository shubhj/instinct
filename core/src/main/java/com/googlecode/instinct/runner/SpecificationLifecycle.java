/*
 * Copyright 2006-2008 Workingmouse
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

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.LifecycleMethod;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import fj.F;
import fj.F2;
import fj.P1;
import fj.data.Either;
import fj.data.List;
import fj.data.Option;
import java.lang.reflect.Field;

/**
 * The lifecycle of a specification. Specifications are run using this lifecycle, in this exact order: <ol> <li>The context class for the
 * specification is created. Each specification gets its own instance.</li> <li>The mockery is reset, all mocks now contain no expectations.</li>
 * <li>Specification actors are auto-wired (see {@link com.googlecode.instinct.actor.ActorAutoWirerImpl}).</li> <li>Before specification methods are
 * run.</li> <li>The specification is run.</li> <li>After specification methods are run.</li> <li>Mock expectations are verified.</li> </ol> The
 * default implementation of each of these steps can be overidden by implementing this interface and annotating your contexts with {@link
 * com.googlecode.instinct.marker.annotate.Context#lifecycle()}. Implementations of <code>SpecificationLifecycle</code> must have a public no-args
 * (nullary) constructor. Before, after and specification methods will be validated (checked they take no parameters) before being passed to the
 * lifecycle implementation. See {@link NoOpSpecificationLifecycle} for an example of how to create a custom lifecycle.
 * @see com.googlecode.instinct.marker.annotate.Context
 * @see com.googlecode.instinct.runner.NoOpSpecificationLifecycle
 * @see com.googlecode.instinct.runner.StandardSpecificationLifecycle
 * @see com.googlecode.instinct.internal.runner.SpecificationRunnerImpl
 * @see com.googlecode.instinct.actor.ActorAutoWirerImpl
 */
@SuppressWarnings({"UnnecessaryFullyQualifiedName"})
public interface SpecificationLifecycle {
    /**
     * Creates the context containing this specification.
     * @return A function to create the context. Takes a class and returns an <var>Option</var> containing the error in <code>some</code> or
     *         <code>none</code> if there was no error.
     */
    <T> F<Class<T>, Either<Throwable, ContextClass>> createContext();

    /**
     * Reset the mockery, all mocks defined in the context  now have no expectations set.
     * @return A product to reset the mockery. Takes an <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was
     *         no error.
     */
    P1<Option<Throwable>> resetMockery();

    /**
     * Auto-wires all marked specification actors (dummies, stubs, mocks & subjects).
     * @return A function to create the context. Takes the instance of the context to wire actors into and returns an <var>Either</var> containing an
     *         error on the left or the list of fields wired on the right.
     */
    F<Object, Either<Throwable, List<Field>>> wireActors();

    /**
     * Runs the before specification methods.
     * @return A function taking the instance of the context to run the before specification methods on, the before specification methods and
     *         returning an <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    F2<Object, List<LifecycleMethod>, Option<Throwable>> runBeforeSpecificationMethods();

    /**
     * Runs the specification.
     * @return A function taking the instance of the context to run the specification method on, the specification method and returning an
     *         <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    F2<Object, SpecificationMethod, Option<Throwable>> runSpecification();

    /**
     * Runs the after specification methods.
     * @return A function taking the instance of the context to run the after specification methods on, the after specification methods and returning
     *         an <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    F2<Object, List<LifecycleMethod>, Option<Throwable>> runAfterSpecificationMethods();

    /**
     * Verify the expectations set on the mocks in this context.
     * @return A product to verify the mockery. Takes an <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was
     *         no error.
     */
    P1<Option<Throwable>> verifyMocks();
}
