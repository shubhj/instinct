package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.marker.annotate.Specification.SpecificationState;

public final class SpecificationRunPendingStatus implements SpecificationRunStatus {
    public Object getDetailedStatus() {
        return SpecificationState.PENDING.toString();
    }

    public boolean runSuccessful() {
        return true;
    }
}
