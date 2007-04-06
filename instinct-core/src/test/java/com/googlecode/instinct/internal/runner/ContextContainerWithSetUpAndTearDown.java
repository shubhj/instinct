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

import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.BehaviourContext;

@SuppressWarnings({"EmptyClass", "UnusedDeclaration"})
public final class ContextContainerWithSetUpAndTearDown {
    @BehaviourContext
    public static class AnEmbeddedPublicContext {
        @BeforeSpecification
        public void aSetUpMethod() {
        }

        @BeforeSpecification
        private void anotherSetUpMethod() {
        }

        @AfterSpecification
        public void aTearDownMethod() {
        }

        @AfterSpecification
        protected void drop() {
        }

        @AfterSpecification
        void down() {
        }

        @AfterSpecification
        private void anotherTearDownMethod() {
        }
    }
}
