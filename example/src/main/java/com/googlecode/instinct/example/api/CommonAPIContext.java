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

package com.googlecode.instinct.example.api;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Arrays.asList;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class CommonAPIContext {

    @Specification
    public void shouldHaveAStandardAPIForArrays() {
        final String[] values = {"one", "two", "three"};
        final String[] empty = {};

        expect.that(values).isNotEmpty();
        expect.that(empty).isEmpty();

        expect.that(values).containsItem("two");
        expect.that(values).doesNotContainItem("four");

        expect.that(values).isOfSize(3);
        expect.that(empty).isOfSize(0);
    }

    @Specification
    public void shouldHaveAStandardAPIForCollections() {
        final Collection<Integer> numbers = new ArrayList<Integer>(asList(1, 2, 3, 4));
        final Collection<String> empty = new ArrayList<String>();

        expect.that(numbers).isNotEmpty();
        expect.that(empty).isEmpty();
    }
}