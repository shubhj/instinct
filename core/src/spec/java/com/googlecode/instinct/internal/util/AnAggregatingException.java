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
import fj.data.List;
import org.junit.runner.RunWith;

@SuppressWarnings({"UnusedDeclaration"})
@RunWith(InstinctRunner.class)
public final class AnAggregatingException {
    @Subject private AggregatingException exception;
    @Stub private String message1;
    @Stub private String message2;

    @BeforeSpecification
    public void before() {
        List<Throwable> results = List.nil();
        try {
            throw new RuntimeException(message1);
        } catch (RuntimeException e) {
            results = results.cons(e);
        }
        try {
            throw new RuntimeException(message2);
        } catch (RuntimeException e) {
            results = results.cons(e);
        }
        exception = new AggregatingException(results);
    }

    public void testConformsToClassTraits() {
        checkClass(AggregatingException.class, RuntimeException.class);
    }

    @Specification
    public void turnsAListOfSpecificationResultsIntoASingleError() {
        expect.that(exception.getMessage()).containsString("The following 2 errors ocurred while running the specification:\n");
        expect.that(exception.getMessage()).containsString("RuntimeException: " + message1);
        expect.that(exception.getMessage()).containsString("RuntimeException: " + message2);
    }
}
