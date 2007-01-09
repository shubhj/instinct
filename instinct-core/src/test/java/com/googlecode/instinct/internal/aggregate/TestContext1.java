package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;

@SuppressWarnings({"ProtectedMemberInFinalClass", "UnusedDeclaration"})
@BehaviourContext
public final class TestContext1 {
    @Specification
    public void whoCares() {
    }

    @Specification
    protected void notMe() {
    }

    @Specification
    private void norMe() {
    }

    @Specification
    void iDo() {
    }
}
