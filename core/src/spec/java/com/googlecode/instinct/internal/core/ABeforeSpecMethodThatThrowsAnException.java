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

package com.googlecode.instinct.internal.core;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration", "TypeMayBeWeakened"})
@RunWith(InstinctRunner.class)
@Context
public final class ABeforeSpecMethodThatThrowsAnException {
    @Specification
    public void shouldFailTheSpecWhenTheExceptionThrownIsTheSameAsTheSpecsExpectedException() {
        final ContextClass contextClass = new ContextClassImpl(BeforeThrowsSameExceptionAsSpec.class);
        final ContextResult result = contextClass.run();
        expect.that(result.completedSuccessfully()).isFalse();
    }

    @Specification
    public void shouldFailTheBeforeThrowsAnException() {
        final ContextClass contextClass = new ContextClassImpl(BeforeThrowsException.class);
        expect.that(contextClass.run().completedSuccessfully()).isFalse();
    }

    private static final class BeforeThrowsSameExceptionAsSpec {
        void before() {
            throw new RuntimeException();
        }

        @Specification(expectedException = RuntimeException.class)
        void spec() {
            throw new RuntimeException();
        }
    }

    private static final class BeforeThrowsException {
        void before() {
            throw new RuntimeException();
        }

        @Specification
        void spec() {
        }
    }
}