package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.verify.Verify.mustBeTrue;

@Suggest({"TechTalk: Drive out a magazine rack, using a mock stack to show interactions.",
        "TechTalk: Use this to show how to do automocking"})
@BehaviourContext
public final class AnEmptyMagazineRack {
    private MagazineRack magazineRack;

    @BeforeSpecification
    public void setUp() {
        magazineRack = new MagazineRackImpl();
    }

    @Specification
    void mustBeEmpty() {
        mustBeTrue(magazineRack.hasMagazines());
    }

    @Specification
    void mustNoLongerBeFullAfterAddingAMagazineToThePile() {

    }
}
