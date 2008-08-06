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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.lang.Primordial;
import com.googlecode.instinct.internal.util.AggregatingException;
import static com.googlecode.instinct.internal.util.Fj.not;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import fj.F;
import static fj.Function.compose;
import fj.data.List;
import static fj.data.List.nil;
import fj.data.Option;
import static fj.data.Option.fromNull;

public final class SpecificationRunFailureStatus extends Primordial implements SpecificationRunStatus {
    private final SpecificationFailureException failure;
    private final Option<Throwable> specificationError;

    public SpecificationRunFailureStatus(final SpecificationFailureException failure, final Option<Throwable> specificationError) {
        checkNotNull(failure, specificationError);
        this.failure = failure;
        this.specificationError = specificationError;
    }

    public SpecificationFailureException getDetails() {
        return failure;
    }

    public Option<Throwable> getSpecificationError() {
        return specificationError;
    }

    public List<Throwable> getLifecycleErrors() {
        if (specificationError.isSome()) {
            if (failure.getCause() instanceof AggregatingException) {
                final List<Throwable> allErrors = ((AggregatingException) failure.getCause()).getAggregatedErrors();
                return allErrors.filter(compose(not(), isSpecificationError()));
            } else {
                return nil();
            }
        } else {
            if (failure.getCause() instanceof AggregatingException) {
                return ((AggregatingException) failure.getCause()).getAggregatedErrors();
            } else {
                final Option<Throwable> cause = fromNull(failure.getCause());
                return cause.isSome() ? List.<Throwable>nil().cons(cause.some()) : List.<Throwable>nil();
            }
        }
    }

    private F<Throwable, Boolean> isSpecificationError() {
        return new F<Throwable, Boolean>() {
            public Boolean f(final Throwable throwable) {
                return !specificationError.isNone() && specificationError.some().equals(throwable);
            }
        };
    }

    public boolean runSuccessful() {
        return false;
    }
}
