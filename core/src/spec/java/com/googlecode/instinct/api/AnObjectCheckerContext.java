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

import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.expect.state.checker.ObjectCheckerImpl;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import java.util.Collection;
import org.hamcrest.Matcher;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsNull;
import static org.hamcrest.core.IsSame.sameInstance;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AnObjectCheckerContext {
    private static final String GREETING = "greeting";
    private static final String BYTES = "bytes";

    @Specification
    public void shouldImplementDoesNotMatchOnAllOf() {
        final int one = 1;
        final ObjectChecker<Integer> objectChecker = new ObjectCheckerImpl<Integer>(one);

        final Collection<Matcher<? extends Integer>> matcherList = new ArrayList<Matcher<? extends Integer>>();
        matcherList.add(equalTo(5));
        matcherList.add(IsNull.<Integer>nullValue());

        objectChecker.matches(equalTo(1), sameInstance(one));
        objectChecker.doesNotMatchOnAllOf(equalTo(2), equalTo(3));
        objectChecker.doesNotMatchOnAllOf(matcherList);
    }

    @Specification
    public void shouldImplementHasBeanProperty() {
        final String greeting = GREETING;
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);

        objectChecker.hasBeanProperty(BYTES);
        final byte[] expectedBytes = greeting.getBytes();
        objectChecker.hasBeanProperty(BYTES, equalTo(expectedBytes));
    }

    @Specification
    public void shouldImplementHasToString() {
        final String greeting = GREETING;
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);

        objectChecker.hasToString(equalTo(GREETING));
    }

    @Specification
    public void shouldImplementTypeChecking() {
        final String greeting = GREETING;
        final String goodbye = "au revoir";
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);

        objectChecker.isAnInstanceOf(String.class);
        objectChecker.isOfType(String.class);
        objectChecker.isNotTheSameInstanceAs(goodbye);
    }

    @Specification
    public void shouldImplementEquality() {
        final String greeting = GREETING;
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);

        objectChecker.isNotNull();
        objectChecker.isEqualTo(GREETING);
        objectChecker.isNotEqualTo("foobar");
    }

    @Specification
    public void shouldImplementMatches() {
        final double doubleValue = 2.5d;
        final ObjectChecker<Double> objectChecker = new ObjectCheckerImpl<Double>(doubleValue);

        final Collection<Matcher<? extends Double>> matcherList = new ArrayList<Matcher<? extends Double>>();
        matcherList.add(IsNull.<Double>notNullValue());
        matcherList.add(is(doubleValue));

        objectChecker.matches(equalTo(2.5d), IsNull.<Double>notNullValue());
        objectChecker.matches(matcherList);

        objectChecker.matchesAnyOf(equalTo(2.4d), equalTo(2.5d), IsNull.<Double>nullValue(), IsNull.<Double>notNullValue());
    }
}