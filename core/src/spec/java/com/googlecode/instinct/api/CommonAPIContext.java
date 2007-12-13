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
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public class CommonAPIContext {

    @Specification
    public void shouldHaveAStandardAPIForArrays() {
        final String[] stringArray = {"one", "two", "three"};
        final String[] emptyStringArray = {};

        expect.that(stringArray).isNotEmpty();
        expect.that(emptyStringArray).isEmpty();

        expect.that(stringArray).containsItem("two");
        expect.that(stringArray).doesNotContainItem("four");

        expect.that(stringArray).isOfSize(3);
        expect.that(emptyStringArray).isOfSize(0);
    }

    @Specification
    public void shouldHaveAStandardAPIForCollections() {
        final Collection<Integer> numbersList = new ArrayList<Integer>(asList(1, 2, 3, 4));
        final Collection<String> emptyList = new ArrayList<String>();

        expect.that(numbersList).isNotEmpty();
        expect.that(emptyList).isEmpty();

        expect.that(numbersList).containsItem(2);
        expect.that(numbersList).containsItems(2, 3);
        expect.that(numbersList).doesNotContainItem(6);
        expect.that(numbersList).doesNotContainItems(5, 6);

        expect.that(numbersList).isOfSize(4);
        expect.that(emptyList).isOfSize(0);

        expect.that(numbersList).hasTheSameContentAs(asList(1, 2, 3, 4));
        expect.that(numbersList).hasTheSameContentAs(1, 2, 3, 4);
    }

    @SuppressWarnings({"TooBroadScope"})
    @Specification
    public void shouldHaveAStandardAPIForStrings() {
        final String stringValue = "A test String";
        final String emptyString = "";

        expect.that(stringValue).isNotEmpty();
        expect.that(emptyString).isEmpty();

        expect.that(stringValue).isOfSize(stringValue.length());
        expect.that(stringValue).isOfLength(stringValue.length());
        expect.that(emptyString).isOfSize(0);
        expect.that(emptyString).isOfLength(0);

        expect.that(stringValue).isEqualToIgnoringCase("a test string");
        expect.that(stringValue).isEqualToIgnoringWhiteSpace("    A test String");

        expect.that(stringValue).isNotEqualToIgnoringCase("another test string");
        expect.that(stringValue).isNotEqualToIgnoringWhiteSpace("    another test String");
    }

    @Specification
    public void shouldHaveAStandardAPIForMaps() {
        final Map<String, Integer> numbersMap = new HashMap<String, Integer>();
        final Map<String, Integer> emptyMap = new HashMap<String, Integer>();

        numbersMap.put("one", 1);
        numbersMap.put("two", 2);
        numbersMap.put("three", 3);

        expect.that(numbersMap).isNotEmpty();
        expect.that(emptyMap).isEmpty();

        expect.that(numbersMap).containsKey("one");
        expect.that(numbersMap).containsEntry("three", 3);
        expect.that(numbersMap).containsValue(1);

        expect.that(emptyMap).doesNotContainKey("one");
        expect.that(emptyMap).doesNotContainEntry("two", 2);
        expect.that(emptyMap).doesNotContainValue(1);
    }

    @SuppressWarnings({"MagicNumber"})
    @Specification
    public void shouldHaveAStandardAPIForComparable() {
        final int value1 = 100;
        final int value2 = 200;

        expect.that(value1).isLessThan(value2);
        expect.that(value2).isGreaterThan(value1);
        expect.that(value1).isGreaterThanOrEqualTo(value1 - 1);
        expect.that(value2).isLessThanOrEqualTo(value2 + 1);
    }

    @SuppressWarnings({"MagicNumber"})
    @Specification
    public void shouldHaveAStandardAPIForDouble() {
        final double doubleValue = 2.0d;

        expect.that(doubleValue).isNotCloseTo(-3.1d, 5.0);
        expect.that(doubleValue).isCloseTo(-2.0d, 5.0);
    }
}