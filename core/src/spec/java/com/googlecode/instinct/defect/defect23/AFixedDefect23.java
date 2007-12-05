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

package com.googlecode.instinct.defect.defect23;

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.defect.defect23.data.ASubContextOverridingExceptionalMethods;
import static com.googlecode.instinct.expect.Expect.expect;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AFixedDefect23 {

    @Specification
    public void shouldReturnOverridenSpecsOnceOnly() {
        final ContextClass contextClass = new ContextClassImpl(ASubContextOverridingExceptionalMethods.class);
        expect.that(contextClass.getSpecificationMethods()).hasSize(2);
    }
}