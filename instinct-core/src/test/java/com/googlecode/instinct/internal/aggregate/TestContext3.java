package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.BeforeSpecification;

@SuppressWarnings({"EmptyClass"})
@BehaviourContext
public final class TestContext3 {

    @BehaviourContext
    public static class AnEmbeddedPublicContext {
        public void whoCares() {
        }
    }

    @SuppressWarnings({"PackageVisibleInnerClass"})
    @BehaviourContext
    static class AnEmbeddedPackageLocalContext {
        public void whoCares() {
        }
    }

    @SuppressWarnings({"UnusedDeclaration"})
    @BehaviourContext
    private static class AnEmbeddedPrivateContext {
        @BeforeSpecification
        public void aSetUpMethod() {
        }
    }
}
