package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.same;

@SuppressWarnings({"unchecked"})
@Suggest({"TechTalk: Drive out a magazine rack, using a mock stack to show interactions.",
        "TechTalk: Use this to show how to do automocking"})
@BehaviourContext
public final class AnEmptyMagazineRack {
    private MagazineRack magazineRack;
    private Stack<Magazine> stack;
    private Magazine magazine;

    @BeforeSpecification
    public void setUp() {
        stack = mock(Stack.class);
        magazine = mock(Magazine.class);
        magazineRack = new MagazineRackImpl(stack);
    }

    @Specification
    void callsPushOnStackWhenAddAMagazineIsAddedToThePile() {
        expects(stack).method("push").with(same(magazine));
        magazineRack.addToPile(magazine);
    }
}
