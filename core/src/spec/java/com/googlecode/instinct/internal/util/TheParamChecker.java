/*
 * Copyright 2008 Jeremy Mawson
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

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class TheParamChecker {

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 0 should not contain whitespace")
    public void willDetectWhitespaceInASingleParameter() {
        ParamChecker.checkNoWhitespace("some whitespace");
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 0 should not contain whitespace")
    public void willDetectWhitespaceInTheFirstParameter() {
        ParamChecker.checkNoWhitespace("some\twhitespace", "some more", "OK");
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 2 should not contain whitespace")
    public void willDetectWhitespaceInASubsequentParameter() {
        ParamChecker.checkNoWhitespace("OK", "still_ok", "then\nsome\twhitespace");
    }

    @Specification
    public void willNotDetectWhitespaceInANullParameter() {
        ParamChecker.checkNoWhitespace(new String[]{null});
    }

    @Specification
    public void willNotDetectWhitespaceWhereThereAreNoParameters() {
        ParamChecker.checkNoWhitespace();
    }
}
