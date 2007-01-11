package com.googlecode.instinct.mock;

import org.jmock.core.Verifiable;

public interface Verifier {
    void addVerifiable(Verifiable verifiable);

    void verify();
}
