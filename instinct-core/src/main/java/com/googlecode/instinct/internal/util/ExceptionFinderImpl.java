package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

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
}
