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

import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;

public class ABaseContextWithExceptionalMethods {

    @BeforeSpecification
    public void setup() {
        throw new IllegalStateException("setup should be called from subclasses");
    }

    @AfterSpecification
    public void tearDown() {
        throw new IllegalStateException("tearDown should be called from subclasses");
    }

    @Specification
    public void shouldBeCalledFromSubclasses() {
        throw new IllegalStateException("shouldBeCalledFromSubclasses should be called from subclasses");
    }
}
