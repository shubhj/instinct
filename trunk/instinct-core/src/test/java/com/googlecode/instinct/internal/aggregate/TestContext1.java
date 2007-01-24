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

package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@SuppressWarnings({"ProtectedMemberInFinalClass", "UnusedDeclaration"})
@BehaviourContext
public final class TestContext1 {
    @Specification
    public void whoCares() {
    }

    @Specification
    protected void notMe() {
    }

    @Specification
    private void norMe() {
    }

    @Specification
    void iDo() {
    }
}
