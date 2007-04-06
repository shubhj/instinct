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

package com.googlecode.instinct.internal.aggregate.locate;

import com.googlecode.instinct.marker.annotate.BehaviourContext;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;

@SuppressWarnings({"UnusedDeclaration", "PackageVisibleField", "FieldCanBeLocal", "ProtectedField"})
@BehaviourContext
public class WithRuntimeAnnotations {
    @Dummy
    private final String string1;
    @Dummy
    final String string2;
    @Dummy
    protected final String string3;
    @Dummy
    public final String string4;
    public final String string5;

    public WithRuntimeAnnotations(final String string1, final String string2, final String string3, final String string4, final String string5) {
        this.string1 = string1;
        this.string2 = string2;
        this.string3 = string3;
        this.string4 = string4;
        this.string5 = string5;
    }

    @Override
    @Specification
    public String toString() {
        return super.toString();
    }

    @Override
    @Specification
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
