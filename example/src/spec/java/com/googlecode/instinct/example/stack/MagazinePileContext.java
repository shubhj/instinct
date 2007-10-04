package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import static com.googlecode.instinct.expect.behaviour.Mocker.verify;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@SuppressWarnings({"unchecked"})
@Suggest({
        "TechTalk: Create MagazineRack interface & class, addToPile().",
        "TechTalk: Add Stack<Magazine> collaborator in constructor, drive out addToPile().",
        "TechTalk: Show how removing fillUpStack() simplifies code but removes explicitness.",
        "TechTalk: Show how annotating mocks & subject adds a level of explicitness."
        })
@RunWith(InstinctRunner.class)
@Context
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
        expect.that(new Expectations() {{
            one(stack).push(magazine);
        }});
        magazinePile.addToPile(magazine);
    }
}
