package com.googlecode.instinct.test.mock;

import org.jmock.core.Verifiable;

public interface Verifier {
    void addVerifiable(Verifiable verifiable);

    void verify();
}
