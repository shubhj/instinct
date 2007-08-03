package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.same;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;

@SuppressWarnings({"unchecked"})
@Suggest({
        "TechTalk: Create MagazineRack interface & class, addToPile().",
        "TechTalk: Add Stack<Magazine> collaborator in constructor, drive out addToPile().",
        "TechTalk: Show how removing fillUpStack() simplifies code but removes explicitness.",
        "TechTalk: Show how annotating mocks & subject adds a level of explicitness."
        })
@Context
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