package com.googlecode.instinct.example.techtalk;

import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.core.annotate.Specification;
import static com.googlecode.instinct.verify.Verify.mustEqual;

@BehaviourContext
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
        mustEqual(title, magazine.getTitle());
    }
}
