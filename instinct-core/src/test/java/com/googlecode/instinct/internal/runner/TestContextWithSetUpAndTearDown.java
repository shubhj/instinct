package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.AfterSpecification;

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
