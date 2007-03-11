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

package com.googlecode.instinct.expect.check;

import org.hamcrest.Matchers;

// TODO Test this
public class StringCheckerImpl extends ComparableCheckerImpl<String> implements StringChecker {

    public StringCheckerImpl(String subject) {
        super(subject);
    }

    public final void equalsIgnoringCase(String string) {
        getAsserter().expectThat(subject, Matchers.equalToIgnoringCase(string));
    }

    public final void equalsIgnoringWhiteSpace(String string) {
        getAsserter().expectThat(subject, Matchers.equalToIgnoringWhiteSpace(string));
    }

    public final void notEqualIgnoringCase(String string) {
        getAsserter().expectNotThat(subject, Matchers.equalToIgnoringCase(string));
    }

    public final void notEqualIgnoringWhiteSpace(String string) {
        getAsserter().expectNotThat(subject, Matchers.equalToIgnoringWhiteSpace(string));
    }

    public final void containsString(String string) {
        getAsserter().expectThat(subject, Matchers.containsString(string));
    }

    public final void notContainString(String string) {
        getAsserter().expectNotThat(subject, Matchers.containsString(string));
    }

    public final void endsWith(String string) {
        getAsserter().expectThat(subject, Matchers.endsWith(string));
    }

    public final void notEndingWith(String string) {
        getAsserter().expectNotThat(subject, Matchers.endsWith(string));
    }

    public final void startsWith(String string) {
        getAsserter().expectThat(subject, Matchers.startsWith(string));
    }

    public final void notStartingWith(String string) {
        getAsserter().expectNotThat(subject, Matchers.startsWith(string));
    }
}
