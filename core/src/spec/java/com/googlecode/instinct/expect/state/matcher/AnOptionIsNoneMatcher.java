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

package com.googlecode.instinct.expect.state.matcher;

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import fj.data.Option;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnOptionIsNoneMatcher {
    @Stub(auto = false) private Option<Integer> someOption;
    @Stub(auto = false) private Option<Integer> noneOption;

    @BeforeSpecification
    public void before() {
        someOption = Option.some(5);
        noneOption = Option.none();
    }

    @Specification
    public void matchesOptionsThatAreNone() {
        assertThat(noneOption, OptionIsNoneMatcher.<Integer>isNone());
    }

    @Specification
    public void doesNotMatchSome() {
        assertThat(someOption, Matchers.not(OptionIsNoneMatcher.<Integer>isNone()));
    }
}
