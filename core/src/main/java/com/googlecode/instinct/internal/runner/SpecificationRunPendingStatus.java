package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.lang.Primordial;
import com.googlecode.instinct.marker.annotate.Specification.SpecificationState;

public final class SpecificationRunPendingStatus extends Primordial implements SpecificationRunStatus {
    public Object getDetailedStatus() {
        return SpecificationState.PENDING;
    }

    public boolean runSuccessful() {
        return true;
    }
}
