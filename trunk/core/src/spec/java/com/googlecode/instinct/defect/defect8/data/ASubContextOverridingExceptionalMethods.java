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

package com.googlecode.instinct.defect.defect8.data;

import com.googlecode.instinct.expect.Expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public class ASubContextOverridingExceptionalMethods extends ABaseContextWithExceptionalMethods {

    @Override
    @BeforeSpecification
    public void setup() {
        //do nothing.
    }

    @Override
    @AfterSpecification
    public void tearDown() {
        //do nothing.
    }

    @Override
    @Specification
    public void shouldBeCalledFromSubclasses() {
        Expect.expect.that(true).isTrue();
    }
}