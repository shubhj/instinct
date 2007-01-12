package com.googlecode.instinct.test;

public final class TestingException extends RuntimeException {
    private static final long serialVersionUID = 1738578664381519847L;

    public TestingException(final Throwable cause) {
        super(cause);
    }

    public TestingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
