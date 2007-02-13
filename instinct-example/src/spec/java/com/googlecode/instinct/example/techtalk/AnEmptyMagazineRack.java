package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.verify.Verify;

@Suggest("Drive out a magazine rack, using a mock stack to show interactions.")
@BehaviourContext
public final class AnEmptyMagazineRack {
    @Specification
    void mustDoSomething() {
        Verify.mustBeTrue(true);
    }
}
