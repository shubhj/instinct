package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
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
        magazineRack = new MagazineRackImpl(stack);
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
