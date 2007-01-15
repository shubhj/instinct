package com.googlecode.instinct.internal.mock;

public final class ExpectationNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 2295895880635773121L;

    public ExpectationNotAllowedException(final Object testDouble) {
        super("Unable to set expectation on non-mockable test double " + testDouble);
    }
}
