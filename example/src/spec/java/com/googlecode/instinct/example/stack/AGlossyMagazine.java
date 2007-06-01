package com.googlecode.instinct.example.stack;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;

@Context
public final class AGlossyMagazine {
    private static final String AMB = "Australian Mountain Bike";
    private static final String GARDENING_AUSTRALIA = "Gardening Australia";

    @Specification
    void mustSaveTheTitleGivenInConstructor() {
        checkSavesTitleGivenInConstructor(AMB);
        checkSavesTitleGivenInConstructor(GARDENING_AUSTRALIA);
    }

    private void checkSavesTitleGivenInConstructor(final String title) {
        final Magazine magazine = new MagazineImpl(title);
        expect.that(magazine.getTitle()).equalTo(title);
    }
}
