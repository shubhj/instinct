package au.id.adams.instinct.internal.runner;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.Specification;

public final class TestContextWithConstructors {

    @BehaviourContext
    public static final class AConstructorWithParameters {
        private final String someField;

        public AConstructorWithParameters(final String someField) {
            this.someField = someField;
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
        public APublicConstructor() {
        }

        @Specification
        public void aSpecification() {
        }
    }
}
