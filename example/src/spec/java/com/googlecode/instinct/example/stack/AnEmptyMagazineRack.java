package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.reset;
import static com.googlecode.instinct.expect.behaviour.Mocker.verify;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnEmptyMagazineRack {
    @Subject private MagazineRack magazineRack;
    @Mock private Stack<Magazine> stack;
    @Dummy Magazine magazine;

    @BeforeSpecification
    public void before() {
        reset();
        magazineRack = new MagazineRackImpl(stack);
    }

    @AfterSpecification
    public void after() {
        verify();
    }

    @Specification
    void callsPushOnStackWhenAddAMagazineIsAddedToThePile() {
        expect.that(new Expectations() {
            {
                one(stack).push(magazine);
            }
        });
        magazineRack.addToPile(magazine);
    }
}
