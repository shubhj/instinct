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

package com.googlecode.instinct.internal.util.exception;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.util.ListUtil;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import fj.data.List;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AListUtil {
    @Stub(auto = false) private List<Integer> integers;

    @BeforeSpecification
    public void before() {
        integers = List.<Integer>nil().cons(3).cons(2).cons(1);
    }

    @Specification
    public void convertsAListToAString() {
        expect.that(ListUtil.listToString(integers)).isEqualTo("[1,2,3]");
    }
}