package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@SuppressWarnings({"EmptyClass", "UnusedDeclaration", "ProtectedMemberInFinalClass"})
public final class ContextContainerWithConstructors {
    @BehaviourContext
    public static final class AConstructorWithParameters {
        public AConstructorWithParameters(final String someField) {
        }

        @Specification
        public void aSpecification() {
        }
    }

    @BehaviourContext
    public static final class APrivateConstructor {
        private APrivateConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }

    @BehaviourContext
    public static final class AProtectedConstructor {
        protected AProtectedConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }

    @BehaviourContext
    public static final class APackageLocalConstructor {
        APackageLocalConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }

    @BehaviourContext
    public static final class APublicConstructor {
        @Specification
        public void aSpecification() {
        }
    }
}
