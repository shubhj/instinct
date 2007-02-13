package com.googlecode.instinct.integrate.junit3;

import junit.framework.AssertionFailedError;

public final class ChainableAssertionFailedError extends AssertionFailedError {
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
