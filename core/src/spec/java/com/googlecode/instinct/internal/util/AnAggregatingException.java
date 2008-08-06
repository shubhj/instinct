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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.F;
import fj.data.List;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class AnAggregatingException {
    @Subject private AggregatingException exception;
    @Stub(auto = false) private String message1;
    @Stub(auto = false) private String message2;

    @BeforeSpecification
    public void before() {
        message1 = "The first exception";
        message2 = "The second exception";
        final List<Throwable> errors = List.<String>nil().cons(message2).cons(message1).map(new F<String, Throwable>() {
            public Throwable f(final String message) {
                try {
                    throw new RuntimeException();
                } catch (RuntimeException nested) {
                    try {
                        throw new RuntimeException(message, nested);
                    } catch (RuntimeException parent) {
                        return parent;
                    }
                }
            }
        });
        exception = new AggregatingException("An error", errors);
    }

    public void testConformsToClassTraits() {
        checkClass(AggregatingException.class, RuntimeException.class);
    }

    @Specification
    public void turnsAListOfSpecificationResultsIntoASingleErrorUsingToString() {
        expect.that(exception.toString()).containsString(AggregatingException.class.getName() + ": 2 error(s) occurred");
        expect.that(exception.toString()).containsString("RuntimeException: " + message1);
        expect.that(exception.toString()).containsString("RuntimeException: " + message2);
    }
}
