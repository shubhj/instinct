package au.id.adams.instinct.internal.aggregate;

import au.id.adams.instinct.core.annotate.BehaviourContext;
import au.id.adams.instinct.core.annotate.Specification;

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
