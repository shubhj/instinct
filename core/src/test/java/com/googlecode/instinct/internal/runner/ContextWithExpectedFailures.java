/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import au.net.netstorm.boost.edge.EdgeException;
import java.lang.reflect.InvocationTargetException;

@Context
public class ContextWithExpectedFailures {
    @Specification(expectedException = RuntimeException.class)
    public void markedAsFailureButDoesNot() {
    }

    @Specification(expectedException = RuntimeException.class)
    public void failsWithoutMessage() {
        throw new RuntimeException();
    }

    @Specification(expectedException = RuntimeException.class, withMessage = "Some message")
    public void failsWithMessage() {
        throw new RuntimeException("Some message");
    }

    @Specification(expectedException = RuntimeException.class, withMessage = "Some message")
    public void failsWithDifferentException() {
        throw new IllegalStateException("Some message");
    }

    @Specification(expectedException = RuntimeException.class, withMessage = "Some message")
    public void failsWithDifferentMessage() {
        throw new RuntimeException("A different message");
    }

    @Specification(expectedException = EdgeException.class)
    public void expectedFailureWithEdgeException() {
        throw new EdgeException(new RuntimeException());
    }

    @Specification(expectedException = EdgeException.class)
    public void expectedFailureWithEdgeExceptionWithInvocationTargetException() {
        throw new EdgeException(new InvocationTargetException(new RuntimeException()));
    }
}
