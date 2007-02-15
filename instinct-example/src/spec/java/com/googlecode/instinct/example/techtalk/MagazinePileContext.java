package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.AfterSpecification;
import com.googlecode.instinct.core.annotate.BeforeSpecification;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.same;
import static com.googlecode.instinct.mock.Mocker.verify;

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
