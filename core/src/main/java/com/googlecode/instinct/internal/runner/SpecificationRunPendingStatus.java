package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.lang.Primordial;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;

public final class SpecificationRunPendingStatus extends Primordial implements SpecificationRunStatus {
    public Object getDetails() {
        return PENDING;
    }

    public boolean runSuccessful() {
        return true;
    }
}
