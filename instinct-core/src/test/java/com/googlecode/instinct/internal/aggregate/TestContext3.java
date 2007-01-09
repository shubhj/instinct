package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;

@SuppressWarnings({"EmptyClass", "PackageVisibleInnerClass", "UnusedDeclaration"})
public final class TestContext3 {
    @BehaviourContext
    public static class AnEmbeddedPublicContext {
        public void whoCares() {
        }
    }

    @BehaviourContext
    static class AnEmbeddedPackageLocalContext {
        public void whoCares() {
        }
    }

    @BehaviourContext
    private static class AnEmbeddedPrivateContext {
        @BeforeSpecification
        public void aSetUpMethod() {
        }
    }
}
