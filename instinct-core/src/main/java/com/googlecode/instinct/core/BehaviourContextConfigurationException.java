package com.googlecode.instinct.core;

public final class BehaviourContextConfigurationException extends RuntimeException {
    private static final long serialVersionUID = -4941355685885692333L;

    public BehaviourContextConfigurationException(final String message) {
        super(message);
    }

    public BehaviourContextConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
