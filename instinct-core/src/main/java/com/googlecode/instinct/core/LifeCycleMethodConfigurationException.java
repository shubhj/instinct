package com.googlecode.instinct.core;

public final class LifeCycleMethodConfigurationException extends RuntimeException {
    private static final long serialVersionUID = -4941355685885692333L;

    public LifeCycleMethodConfigurationException(final String message) {
        super(message);
    }

    public LifeCycleMethodConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
