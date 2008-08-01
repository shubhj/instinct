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

import static com.googlecode.instinct.expect.state.matcher.ListEqualityMatcher.equalTo;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import fj.data.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AListEqualityMatcher {
    @Stub(auto = false) private List<Integer> list1;
    @Stub(auto = false) private List<Integer> list2;
    @Stub(auto = false) private List<Integer> list3;

    @BeforeSpecification
    public void before() {
        list1 = List.<Integer>nil().cons(1);
        list2 = List.<Integer>nil().cons(1);
        list3 = List.<Integer>nil().cons(2);
    }

    @Specification
    public void matchesListsThatAreEqual() {
        assertThat(list1, equalTo(list2));
    }

    @Specification
    public void doesNotMatchListsThatAreNotEqual() {
        assertThat(list1, not(equalTo(list3)));
    }
}