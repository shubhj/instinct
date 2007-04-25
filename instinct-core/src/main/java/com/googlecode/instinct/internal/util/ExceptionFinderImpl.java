package com.googlecode.instinct.internal.util;

import java.lang.reflect.InvocationTargetException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import au.net.netstorm.boost.edge.EdgeException;

@Suggest("May need to put smarts in to detect InvocationTargetException & EdgeExceptionsT")
public final class ExceptionFinderImpl implements ExceptionFinder {
    @Suggest("Use recursion to go arbitrarily deep.")
    public Throwable getRootCause(final Throwable topLevelCause) {
        checkNotNull(topLevelCause);
        if (topLevelCause.getCause() != null) {
            if (topLevelCause.getCause().getCause() != null) {
                return topLevelCause.getCause().getCause();
            } else {
                return topLevelCause.getCause();
            }
        } else {
            return topLevelCause;
        }
    }

    @Suggest("Can we use the above method to do this?")
    @SuppressWarnings({"ProhibitedExceptionThrown"})
    public void rethrowRealError(final EdgeException e) {
        if (e.getCause() instanceof InvocationTargetException) {
            throw (RuntimeException) e.getCause().getCause();
        } else {
            throw e;
        }
    }
}
