/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.util;

import com.googlecode.instinct.internal.edge.EdgeException;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.lang.reflect.InvocationTargetException;

@Suggest("May need to put smarts in to detect InvocationTargetException & EdgeExceptions")
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
