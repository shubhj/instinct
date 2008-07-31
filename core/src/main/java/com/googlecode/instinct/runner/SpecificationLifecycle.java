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
import com.googlecode.instinct.internal.runner.SpecificationResult;
import fj.Unit;
import fj.data.Either;

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
 * {@link com.googlecode.instinct.marker.annotate.Context}:
 * <code><pre>
 * @Context(lifecycle = CustomLifecycle.class)
 * final class AClassWithACustomLifecycle {
 *   @Specification
 *   void runsWithCustomLifecycle() { }
 * }
 * </pre></code>
 * @see com.googlecode.instinct.marker.annotate.Context
 * @see com.googlecode.instinct.internal.runner.NewSpecificationRunnerImpl
 * @see com.googlecode.instinct.actor.ActorAutoWirerImpl
 */
@SuppressWarnings({"UnnecessaryFullyQualifiedName"})
public interface SpecificationLifecycle {
    /**
     * Creates the context containing this specification.
     * @param <T> The exception thrown as a result of creating the context.
     * @return An <var>Either</var> containing an error on the left or the context on the right.
     */
    <T extends Throwable> Either<T, ContextClass> createContext();

    /**
     * Reset the mockery, all mocks defined in the context  now have no expectations set.
     * @param <T> The exception thrown as a result of resetting the mockery.
     * @return An <var>Either</var> containing an error on the left or <var>Unit</var> (void) on the right.
     */
    <T extends Throwable> Either<T, Unit> resetMockery();

    /**
     * Auto-wires all marked specification actors (dummies, stubs, mocks & subjects).
     * @param <T> The exception thrown as a result of wiring the specification actors.
     * @return An <var>Either</var> containing an error on the left or <var>Unit</var> (void) on the right.
     */
    <T extends Throwable> Either<T, Unit> wireActors();

    /**
     * Runs the before specification method.
     * @param <T> The exception thrown as a result of running the before specification methods.
     * @return An <var>Either</var> containing an error on the left or <var>Unit</var> (void) on the right.
     */
    <T extends Throwable> Either<T, Unit> runBeforeSpecificationMethods();

    /**
     * Runs the specification.
     * @param <T> The exception thrown as a result of running the specification.
     * @return An <var>Either</var> containing an error on the left or the result of running the specification on the right.
     */
    <T extends Throwable> Either<T, SpecificationResult> runSpecification();

    /**
     * Runs the after specification method.
     * @param <T> The exception thrown as a result of running the after specification methods.
     * @return An <var>Either</var> containing an error on the left or <var>Unit</var> (void) on the right.
     */
    <T extends Throwable> Either<T, Unit> runAfterSpecificationMethods();

    /**
     * Verify the expectations set on the mocks in this context.
     * @param <T> The exception thrown as a result of verifying the mocks.
     * @return An <var>Either</var> containing an error on the left or <var>Unit</var> (void) on the right.
     */
    <T extends Throwable> Either<T, Unit> verifyMocks();
}
