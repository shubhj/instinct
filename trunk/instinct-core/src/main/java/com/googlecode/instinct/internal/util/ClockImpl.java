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

import com.googlecode.instinct.internal.edge.java.lang.SystemEdge;
import com.googlecode.instinct.internal.edge.java.lang.SystemEdgeImpl;

public final class ClockImpl implements Clock {
    private final SystemEdge systemEdge = new SystemEdgeImpl();

    public long getCurrentTime() {
        return systemEdge.currentTimeMillis();
    }

    public long getElapsedTime(final long startTime) {
        return getCurrentTime() - startTime;
    }
}
