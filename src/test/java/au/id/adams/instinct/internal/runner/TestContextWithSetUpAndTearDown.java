package au.id.adams.instinct.internal.runner;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.BeforeTest;
import au.id.adams.instinct.core.annotate.AfterTest;

@SuppressWarnings({"EmptyClass"})
@BehaviourContext
public final class TestContextWithSetUpAndTearDown {

    @BehaviourContext
    public static class AnEmbeddedPublicContext {
        @BeforeTest
        public void aSetUpMethod() {
        }

        @BeforeTest
        private void anotherSetUpMethod() {
        }

        @AfterTest
        public void aTearDownMethod() {
        }

        @AfterTest
        protected void drop() {
        }

        @AfterTest
        void down() {
        }

        @AfterTest
        private void anotherTearDownMethod() {
        }
    }
}
