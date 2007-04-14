package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ExceptionFinderImpl implements ExceptionFinder {
    @Suggest("Use recursion to go arbitrarily deep.")
    public Throwable getRootCause(final Throwable topLevelCause) {
        checkNotNull(topLevelCause);
        if (topLevelCause.getCause() != null) {
            return topLevelCause.getCause().getCause() != null ? topLevelCause.getCause().getCause() : topLevelCause.getCause();
        } else {
            return topLevelCause;
        }
    }
}
