package com.googlecode.instinct.internal.mock;

import org.jmock.core.Verifiable;

public interface TestDoubleControl extends Verifiable {
    Object createDoubleObject();
}
