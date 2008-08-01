/*
 * Copyright 2008 Tom Adams
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

package com.googlecode.instinct.expect.state.matcher;

import static com.googlecode.instinct.expect.state.matcher.ExistentialListMatcher.hasItemInList;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import fj.F;
import fj.data.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AItemInListMatcher {
    @Stub(auto = false) private List<Integer> list;

    @BeforeSpecification
    public void before() {
        list = List.<Integer>nil().cons(1).cons(2).cons(3);
    }

    @Specification
    public void matchesItemsBasedOnAFunction() {
        assertThat(list, hasItemInList(new F<Integer, Boolean>() {
            public Boolean f(final Integer integer) {
                return integer == 3;
            }
        }));
    }

    @Specification
    public void matchesItemsThatAreInTheList() {
        assertThat(list, hasItemInList(1));
    }

    @Specification
    public void doesNotMatchItemsThatAreNotInTheList() {
        assertThat(list, not(hasItemInList(0)));
    }
}