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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.lang.Primordial;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"Implement readObject() & writeObject() to ensure only ever one VERIFICATION_SUCCESS",
        "Can this be implemented as an enum? Perhaps this becomes an enum & the methods go into the SpecificationResult."})
public final class SpecificationRunSuccessStatus extends Primordial implements SpecificationRunStatus {
    public static final SpecificationRunStatus SPECIFICATION_SUCCESS = new SpecificationRunSuccessStatus();

    public Object getDetails() {
        return "Specification of behaviour verified correctly";
    }

    public boolean runSuccessful() {
        return true;
    }
}
