package com.googlecode.instinct.mock;

import java.util.List;
import java.util.ArrayList;
import org.jmock.core.Verifiable;

public final class VerifierImpl implements Verifier {
    private final List<Verifiable> verifiables = new ArrayList<Verifiable>();

    public void addVerifiable(final Verifiable verifiable) {
        verifiables.add(verifiable);
    }

    public void verify() {
        for (final Verifiable verifiable : verifiables) {
            verifiable.verify();
        }
    }
}
