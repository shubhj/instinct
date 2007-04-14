package com.googlecode.instinct.internal.util;

public interface ExceptionFinder {
    Throwable getRootCause(Throwable topLevelCause);
}
