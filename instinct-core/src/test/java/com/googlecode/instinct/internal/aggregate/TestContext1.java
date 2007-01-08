package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@BehaviourContext
public final class TestContext1 {
    @Specification
    public void whoCares() {
    }

    @SuppressWarnings({"ProtectedMemberInFinalClass"})
    @Specification
    protected void notMe() {
    }

    @SuppressWarnings({"UnusedDeclaration"})
    @Specification
    private void norMe() {
    }

    @Specification
    void iDo() {
    }
}
