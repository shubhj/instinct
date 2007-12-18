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

package com.googlecode.instinct.internal.locate;

import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;

@SuppressWarnings({"UnusedDeclaration", "PackageVisibleField", "FieldCanBeLocal", "ProtectedField", "EqualsAndHashcode"})
@Context
public class WithRuntimeAnnotations {
    @Stub protected String string1;
    @Stub public String string2;
    @Stub public String string3;
    @Stub String string4;
    @Stub private String string5;

    @Override
    @Specification
    public String toString() {
        return super.toString();
    }

    @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
    @Override
    @Specification
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
