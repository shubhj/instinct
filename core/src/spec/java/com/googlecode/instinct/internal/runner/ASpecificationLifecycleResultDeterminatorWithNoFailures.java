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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import fj.Unit;
import static fj.Unit.unit;
import fj.data.Either;
import static fj.data.Either.right;
import fj.data.List;
import static org.hamcrest.Matchers.sameInstance;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ASpecificationLifecycleResultDeterminatorWithNoFailures {
    @Subject private SpecificationLifecycleResultDeterminator determinator;
    @Dummy SpecificationResult specificationResult;
    @Stub(auto = false) Either<Throwable, Unit> createContextResult;
    @Stub(auto = false) Either<Throwable, Unit> restMockeryResult;
    @Stub(auto = false) Either<Throwable, Unit> wireActorsResult;
    @Stub(auto = false) Either<Throwable, Unit> runBeforeSpecificationMethodsResult;
    @Stub(auto = false) Either<Throwable, SpecificationResult> runSpecificationResult;
    @Stub(auto = false) Either<Throwable, Unit> runAfterSpecificationMethodsResult;
    @Stub(auto = false) Either<Throwable, Unit> verifyMocksResult;

    @BeforeSpecification
    public void before() {
        createContextResult = right(unit());
        restMockeryResult = right(unit());
        wireActorsResult = right(unit());
        runBeforeSpecificationMethodsResult = right(unit());
        runSpecificationResult = right(specificationResult);
        runAfterSpecificationMethodsResult = right(unit());
        verifyMocksResult = right(unit());
        determinator = new SpecificationLifecycleResultDeterminator();
    }

    @Specification
    public void returnsTheResultOnTheRight() {
        final Either<List<Throwable>, SpecificationResult> result = determinator
                .result(createContextResult, restMockeryResult, wireActorsResult, runBeforeSpecificationMethodsResult, runSpecificationResult,
                        runAfterSpecificationMethodsResult, verifyMocksResult);
        expect.that(result).isRight(sameInstance(specificationResult));
    }
}
