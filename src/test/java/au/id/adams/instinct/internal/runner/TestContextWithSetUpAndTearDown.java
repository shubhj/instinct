package au.id.adams.instinct.internal.runner;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.BeforeSpecification;
import au.id.adams.instinct.core.annotate.AfterSpecification;

@SuppressWarnings({"EmptyClass"})
@BehaviourContext
public final class TestContextWithSetUpAndTearDown {

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
