/*
 * Copyright 2006-2008 Tom Adams
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

public final class SpecificationLifecycleImpl implements SpecificationLifecycle {
    public <T extends Throwable> Either<T, ContextClass> createContext() {
        throw new UnsupportedOperationException();
    }

    public <T extends Throwable> Either<T, Unit> resetMockery() {
        throw new UnsupportedOperationException();
    }

    public <T extends Throwable> Either<T, Unit> wireActors() {
        throw new UnsupportedOperationException();
    }

    public <T extends Throwable> Either<T, Unit> runBeforeSpecificationMethods() {
        throw new UnsupportedOperationException();
    }

    public <T extends Throwable> Either<T, SpecificationResult> runSpecification() {
        throw new UnsupportedOperationException();
    }

    public <T extends Throwable> Either<T, Unit> runAfterSpecificationMethods() {
        throw new UnsupportedOperationException();
    }

    public <T extends Throwable> Either<T, Unit> verifyMocks() {
        throw new UnsupportedOperationException();
    }
}
