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
import fj.data.Either;
import fj.data.List;
import fj.data.Option;
import java.lang.reflect.Field;

/**
 * The lifecycle of a specification. Specifications are run using this lifecycle, in this exact order:
 * <ol>
 * <li>The context class for the specification is created. Each specification gets its own instance.</li>
 * <li>The mockery is reset, all mocks now contain no expectations.</li>
 * <li>Specification actors are auto-wired (see {@link com.googlecode.instinct.actor.ActorAutoWirerImpl}).</li>
 * <li>Before specification methods are run.</li>
 * <li>The specification is run.</li>
 * <li>After specification methods are run.</li>
 * <li>Mock expectations are verified.</li>
 * </ol>
 * The default implementation of each of these steps can be overidden by implementing this interface and annotating your contexts with
 * {@link com.googlecode.instinct.marker.annotate.Context#lifecycle()}.
 * Implementations of <code>SpecificationLifecycle</code> must have a public no-args (nullary) constructor. Before, after and specification methods
 * will be validated (checked they take no parameters) before being passed to the lifecycle implementation.
 * @see com.googlecode.instinct.marker.annotate.Context
 * @see com.googlecode.instinct.internal.runner.SpecificationRunnerImpl
 * @see com.googlecode.instinct.actor.ActorAutoWirerImpl
 */
@SuppressWarnings({"UnnecessaryFullyQualifiedName"})
public interface SpecificationLifecycle {
    /**
     * Creates the context containing this specification.
     * @param contextClass The class containing the specification.
     * @return An <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    <T> Either<Throwable, ContextClass> createContext(Class<T> contextClass);

    /**
     * Reset the mockery, all mocks defined in the context  now have no expectations set.
     * @return An <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    Option<Throwable> resetMockery();

    /**
     * Auto-wires all marked specification actors (dummies, stubs, mocks & subjects).
     * @param contextInstance The instance of the context to wire actors into.
     * @return An <var>Either</var> containing an error on the left or the list of fields wired on the right.
     */
    Either<Throwable, List<Field>> wireActors(Object contextInstance);

    /**
     * Runs the before specification method.
     * @param contextInstance The instance of the context to run before specification methods on.
     * @param beforeSpecificationMethods The before specification methods to run.
     * @return An <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    Option<Throwable> runBeforeSpecificationMethods(Object contextInstance, List<LifecycleMethod> beforeSpecificationMethods);

    /**
     * Runs the specification.
     * @param contextInstance The instance of the context to run the specification method on.
     * @param specificationMethod The specification method to run.
     * @return An <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    Option<Throwable> runSpecification(Object contextInstance, SpecificationMethod specificationMethod);

    /**
     * Runs the after specification method.
     * @param contextInstance The instance of the context to run after specification methods on.
     * @param afterSpecificationMethods The after specification methods to run.
     * @return An <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    Option<Throwable> runAfterSpecificationMethods(Object contextInstance, List<LifecycleMethod> afterSpecificationMethods);

    /**
     * Verify the expectations set on the mocks in this context.
     * @return An <var>Option</var> containing the error in <code>some</code> or <code>none</code> if there was no error.
     */
    Option<Throwable> verifyMocks();
}
