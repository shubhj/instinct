package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.same;

@SuppressWarnings({"unchecked"})
@Suggest({
        "TechTalk: Create MagazineRack interface & class, addToPile().",
        "TechTalk: Add Stack<Magazine> collaborator in constructor, drive out addToPile().",
        "TechTalk: Show how removing setUp() simplifies code but removes explicitness.",
        "TechTalk: Show how annotating mocks & subject adds a level of explicitness."
        })
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
