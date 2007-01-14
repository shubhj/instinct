package com.googlecode.instinct.internal.util.instance;

public final class ObjectCreationException extends RuntimeException {
    private static final long serialVersionUID = -5779614900053228294L;

    public ObjectCreationException(final String message) {
        super(message);
    }

    public ObjectCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
