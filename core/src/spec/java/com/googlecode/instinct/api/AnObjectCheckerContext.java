package com.googlecode.instinct.api;

import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.expect.state.checker.ObjectCheckerImpl;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import java.util.Collection;
import org.hamcrest.Matcher;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsNull;
import static org.hamcrest.core.IsSame.sameInstance;
import org.junit.runner.RunWith;

/*
 * Copyright 2006-2007 Workingmouse
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

@RunWith(InstinctRunner.class)
@Context
public class AnObjectCheckerContext {

    @Specification
    public void shouldImplementDoesNotMatchOn() {
        final int one = 1;
        final ObjectChecker<Integer> objectChecker = new ObjectCheckerImpl<Integer>(one);

        final Collection<Matcher<? extends Integer>> matcherList = new ArrayList<Matcher<? extends Integer>>();
        matcherList.add(equalTo(5));
        matcherList.add(IsNull.<Integer>nullValue());

        objectChecker.matches(equalTo(1), sameInstance(one));
        objectChecker.doesNotMatchOn(equalTo(2), equalTo(3));
        objectChecker.doesNotMatchOn(matcherList);
    }
}