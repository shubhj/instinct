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

import com.googlecode.instinct.marker.annotate.Specification;

@SuppressWarnings({"EmptyClass", "UnusedDeclaration", "ProtectedMemberInFinalClass", "ClassMayBeInterface"})
public final class ContextContainerWithConstructors {
    public static final class AConstructorWithParameters {
        public AConstructorWithParameters(final String someField) {
        }

        @Specification
        public void aSpecification() {
        }
    }

    public static final class APrivateConstructor {
        private APrivateConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }

    public static final class AProtectedConstructor {
        protected AProtectedConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }

    public static final class APackageLocalConstructor {
        APackageLocalConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }

    public static final class APublicConstructor {
        @Specification
        public void aSpecification() {
        }
    }
}
