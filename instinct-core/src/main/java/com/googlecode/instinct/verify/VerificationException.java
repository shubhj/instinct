package com.googlecode.instinct.verify;

public final class VerificationException extends RuntimeException {
    private static final long serialVersionUID = -9093032518381041627L;

    public VerificationException(final String message) {
        super(message);
    }

    public VerificationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public VerificationException(final Throwable cause) {
        super(cause);
    }
}
