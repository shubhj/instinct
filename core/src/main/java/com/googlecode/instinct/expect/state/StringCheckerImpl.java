/*
 * Copyright 2006-2007 Ben Warren
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

package com.googlecode.instinct.expect.state;

import com.googlecode.instinct.internal.util.Suggest;
import org.hamcrest.Matchers;

@Suggest("Test this.")
public class StringCheckerImpl extends ComparableCheckerImpl<String> implements StringChecker {
    public StringCheckerImpl(final String subject) {
        super(subject);
    }

    public final void equalsIgnoringCase(final String string) {
        nullCheckString(string, "equalsIgnoringCase");
        getAsserter().expectThat(subject, Matchers.equalToIgnoringCase(string));
    }

    public final void equalsIgnoringWhiteSpace(final String string) {
        nullCheckString(string, "equalsIgnoringWhiteSpace");
        getAsserter().expectThat(subject, Matchers.equalToIgnoringWhiteSpace(string));
    }

    public final void notEqualIgnoringCase(final String string) {
        getAsserter().expectNotThat(subject, Matchers.equalToIgnoringCase(string));
    }

    public final void notEqualIgnoringWhiteSpace(final String string) {
        getAsserter().expectNotThat(subject, Matchers.equalToIgnoringWhiteSpace(string));
    }

    public final void containsString(final String string) {
        nullCheckString(string, "containsString");
        getAsserter().expectThat(subject, Matchers.containsString(string));
    }

    public final void notContainString(final String string) {
        getAsserter().expectNotThat(subject, Matchers.containsString(string));
    }

    public final void endsWith(final String string) {
        nullCheckString(string, "endsWith");
        getAsserter().expectThat(subject, Matchers.endsWith(string));
    }

    public final void notEndingWith(final String string) {
        getAsserter().expectNotThat(subject, Matchers.endsWith(string));
    }

    public final void startsWith(final String string) {
        getAsserter().expectThat(subject, Matchers.startsWith(string));
    }

    public final void notStartingWith(final String string) {
        getAsserter().expectNotThat(subject, Matchers.startsWith(string));
    }

    public final void isEmpty() {
        getAsserter().expectThat(subject, StringLengthMatcher.hasLength(0));
    }

    public final void notEmpty() {
        getAsserter().expectThat(subject, Matchers.not(StringLengthMatcher.hasLength(0)));
    }

    public final void hasLength(final int length) {
        getAsserter().expectThat(subject, StringLengthMatcher.hasLength(length));
    }

    private void nullCheckString(final String stringToCheck, final String methodName) {
        if (stringToCheck == null) {
            throw new IllegalArgumentException("Cannot pass a null string into " + methodName);
        }
    }
}
