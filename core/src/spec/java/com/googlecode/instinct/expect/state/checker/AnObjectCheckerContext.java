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

package com.googlecode.instinct.expect.state.checker;

import com.googlecode.instinct.data.Person;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import fj.data.List;
import java.util.ArrayList;
import java.util.Collection;
import org.hamcrest.Matcher;
import static org.hamcrest.core.Is.is;
import org.hamcrest.core.IsNull;
import static org.hamcrest.core.IsSame.sameInstance;
import org.junit.runner.RunWith;

@SuppressWarnings({"unchecked"})
@RunWith(InstinctRunner.class)
@Context
public final class AnObjectCheckerContext {
    @Stub(auto = false) private static final String greeting = "greeting";
    @Stub(auto = false) private List<Integer> list1;
    @Stub(auto = false) private List<Integer> list2;
    @Stub(auto = false) private Integer int1;
    @Stub(auto = false) private Integer int2;

    @BeforeSpecification
    public void before() {
        list1 = List.<Integer>nil().cons(1);
        list2 = List.<Integer>nil().cons(1);
        int1 = 1;
        int2 = 1;
    }

    @Specification
    public void canDetermineEqualityOnNull() {
        new ObjectCheckerImpl<Object>(null).isEqualTo(null);
    }

    @Specification
    public void matchesListsThatAreEqual() {
        new ObjectCheckerImpl<List<Integer>>(list1).isEqualTo(list2);
    }

    @Specification
    public void matchesNonListsThatAreEqual() {
        new ObjectCheckerImpl<Integer>(int1).isEqualTo(int2);
    }

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
        final Person person = new Person("Perry");
        final ObjectChecker<Person> objectChecker = new ObjectCheckerImpl<Person>(person);
        objectChecker.hasBeanProperty("name", String.class);
        objectChecker.hasBeanPropertyWithValue("name", String.class, equalTo("Perry"));
    }

    @Specification
    public void shouldImplementHasToString() {
        final String greeting = AnObjectCheckerContext.greeting;
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);
        objectChecker.hasToString(equalTo(AnObjectCheckerContext.greeting));
    }

    @Specification
    public void shouldImplementTypeChecking() {
        final String greeting = AnObjectCheckerContext.greeting;
        final String goodbye = "au revoir";
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);
        objectChecker.isAnInstanceOf(String.class);
        objectChecker.isOfType(String.class);
        objectChecker.isNotTheSameInstanceAs(goodbye);
    }

    @Specification
    public void shouldImplementEquality() {
        final String greeting = AnObjectCheckerContext.greeting;
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(greeting);
        objectChecker.isNotNull();
        objectChecker.isEqualTo(AnObjectCheckerContext.greeting);
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
        objectChecker.matchesAnyOf(equalTo(2.4d), equalTo(2.5d), IsNull.<Double>nullValue(),
                IsNull.<Double>notNullValue());
    }
}