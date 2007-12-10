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

package com.googlecode.instinct.api;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
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

        expect.that(numbers).containsItem(2);
        expect.that(numbers).containsItems(2, 3);
        expect.that(numbers).doesNotContainItem(6);
        expect.that(numbers).doesNotContainItems(5, 6);

        expect.that(numbers).isOfSize(4);
        expect.that(empty).isOfSize(0);

        expect.that(numbers).hasTheSameContentAs(asList(1, 2, 3, 4));
        expect.that(numbers).hasTheSameContentAs(1, 2, 3, 4);
    }

    @SuppressWarnings({"TooBroadScope"})
    @Specification
    public void shouldHaveAStandardAPIForStrings() {
        final String value = "A test String";
        final String empty = "";

        expect.that(value).isNotEmpty();
        expect.that(empty).isEmpty();

        expect.that(value).isOfSize(value.length());
        expect.that(value).isOfLength(value.length());
        expect.that(empty).isOfSize(0);
        expect.that(empty).isOfLength(0);

        expect.that(value).isEqualToIgnoringCase("a test string");
        expect.that(value).isEqualToIgnoringWhiteSpace("    A test String");

        expect.that(value).isNotEqualToIgnoringCase("another test string");
        expect.that(value).isNotEqualToIgnoringWhiteSpace("    another test String");
    }
}