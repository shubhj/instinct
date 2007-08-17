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

package com.googlecode.instinct.internal.mock;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.jmock.core.Invocation;
import org.jmock.core.InvocationMatcher;

public final class InvokeAnyTimesMatcher implements InvocationMatcher {
    public boolean matches(final Invocation invocation) {
        checkNotNull(invocation);
        return true;
    }

    public void invoked(final Invocation invocation) {
        checkNotNull(invocation);
    }

    public boolean hasDescription() {
        return false;
    }

    public void verify() {
        // Don't do any verification, as anything is fine.
    }

    public StringBuffer describeTo(final StringBuffer buffer) {
        checkNotNull(buffer);
        return buffer;
    }
}
