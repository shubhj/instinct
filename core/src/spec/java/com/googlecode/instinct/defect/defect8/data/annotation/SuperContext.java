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

package com.googlecode.instinct.defect.defect8.data.annotation;

import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;

@SuppressWarnings({"AbstractClassWithoutAbstractMethods"})
public abstract class SuperContext {
    private boolean flag;

    @BeforeSpecification
    public void setup() {
        flag = true;
    }

    public boolean isFlag() {
        return flag;
    }

    @AfterSpecification
    public void tearDown() {
        throw new RuntimeException("Indicates that @AfterSpecification was invoked.");
    }
}
