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

package com.googlecode.instinct.defect.defect3;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.List;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AFixedDefect3 {

    @Specification
    public void shouldMatchContentsOfACollection() {
        final List<String> cities = new ArrayList<String>();
        cities.add("Brisbane");
        cities.add("Sydney");
        cities.add("Melbourne");
        cities.add("Adelaide");

        final List<String> expectedCities = new ArrayList<String>();
        expectedCities.add("Adelaide");
        expectFailureWith(cities, expectedCities);
        expectedCities.add("Brisbane");
        expectFailureWith(cities, expectedCities);
        expectedCities.add("Sydney");
        expectFailureWith(cities, expectedCities);
        expectedCities.add("Melbourne");
        expectSuccessWith(cities, expectedCities);
    }

    @Specification
    public void shouldMatchContentsOfAnotherCollection() {
        final List<String> countries = new ArrayList<String>();
        countries.add("Australia");
        countries.add("UK");

        final List<String> expectedCountries = new ArrayList<String>();
        expectedCountries.add("Australia");
        expectFailureWith(countries, expectedCountries);
        expectedCountries.add("UK");
        expectSuccessWith(countries, expectedCountries);
    }

    @Specification
    public void shouldMatchContentsOfAVarArg() {
        final List<Integer> integers = new ArrayList<Integer>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        expectSuccessWith(integers, asList(2, 3, 1));
        expectSuccessWith(integers, asList(3, 2, 1));
        expectSuccessWith(integers, asList(1, 2, 3));

        expectFailureWith(integers, asList(0, 1, 2, 3));
        expectFailureWith(integers, asList(1, 2, 3, 5));
        expectFailureWith(integers, asList(1, 2, 3, 4));
        expectFailureWith(integers, asList(2, 3, 4));
        expectFailureWith(integers, asList(1, 2, 4));
        expectFailureWith(integers, asList(0, 2, 3));
    }

    private <T> void expectSuccessWith(final Collection<T> values, final Collection<T> expectedValues) {
        expect.that(values).hasTheSameContentAs(expectedValues);
    }

    @SuppressWarnings({"ErrorNotRethrown"})
    private <T> void expectFailureWith(final Collection<T> values, final Collection<T> expectedValues) {
        try {
            expect.that(values).hasTheSameContentAs(expectedValues);
            throw new RuntimeException("Excepted hasTheSameContentAs to fail with incomplete Collection of values");
        } catch (AssertionError e) { }
    }
}