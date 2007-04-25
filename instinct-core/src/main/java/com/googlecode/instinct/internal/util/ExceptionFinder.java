package com.googlecode.instinct.internal.util;

import au.net.netstorm.boost.edge.EdgeException;

public interface ExceptionFinder {
    Throwable getRootCause(Throwable topLevelCause);

    void rethrowRealError(final EdgeException e);
}
