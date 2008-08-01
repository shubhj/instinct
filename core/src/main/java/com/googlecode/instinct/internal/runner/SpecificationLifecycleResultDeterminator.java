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

package com.googlecode.instinct.internal.runner;

import fj.Unit;
import fj.data.Either;
import static fj.data.Either.lefts;
import fj.data.List;

@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
public final class SpecificationLifecycleResultDeterminator {
    public <T extends Throwable> Either<List<T>, SpecificationResult> determineLifecycleResult(final Either<T, Unit> createContextResult,
            final Either<T, Unit> restMockeryResult, final Either<T, Unit> wireActorsResult,
            final Either<T, Unit> runBeforeSpecificationMethodsResult, final Either<T, SpecificationResult> runSpecificationResult,
            final Either<T, Unit> runAfterSpecificationMethodsResult, final Either<T, Unit> verifyMocksResult) {
        final List<Either<T, ?>> results = List.<Either<T, ?>>nil().cons(createContextResult).cons(restMockeryResult).cons(wireActorsResult)
                .cons(runBeforeSpecificationMethodsResult).cons(runSpecificationResult).cons(runAfterSpecificationMethodsResult)
                .cons(verifyMocksResult);
        final List<T> errors = lefts(results);
        return errors.isNotEmpty() ? Either.<List<T>, SpecificationResult>left(errors)
                : Either.<List<T>, SpecificationResult>right(runSpecificationResult.right().value());
    }
}
