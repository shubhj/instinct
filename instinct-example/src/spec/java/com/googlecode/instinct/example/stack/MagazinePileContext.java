package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.same;
import static com.googlecode.instinct.expect.Mocker.verify;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.BehaviourContext;
import com.googlecode.instinct.marker.annotate.Specification;

@SuppressWarnings({"unchecked"})
@Suggest({
        "TechTalk: Create MagazineRack interface & class, addToPile().",
        "TechTalk: Add Stack<Magazine> collaborator in constructor, drive out addToPile().",
        "TechTalk: Show how removing fillUpStack() simplifies code but removes explicitness.",
        "TechTalk: Show how annotating mocks & subject adds a level of explicitness."
        })
@BehaviourContext
public final class MagazinePileContext {
    private Magazine magazine;
    private Stack<Magazine> stack;
    private MagazinePile magazinePile;

    @BeforeSpecification
    public void setup() {
        magazine = mock(Magazine.class);
        stack = mock(Stack.class);
        magazinePile = new MagazinePileImpl(stack);
    }

    @AfterSpecification
    public void tearDown() {
        verify();
    }

    @Specification
    void callsPushOnStackWhenAddAMagazineIsAddedToThePile() {
        expects(stack).method("push").with(same(magazine));
        magazinePile.addToPile(magazine);
    }
}
