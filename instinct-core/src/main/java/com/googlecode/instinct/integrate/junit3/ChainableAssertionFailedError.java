package com.googlecode.instinct.integrate.junit3;

import junit.framework.AssertionFailedError;

public final class ChainableAssertionFailedError extends AssertionFailedError {
    private static final long serialVersionUID = 6154847202698828840L;
    private final Throwable cause;

    public ChainableAssertionFailedError(final Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
