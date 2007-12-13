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

package com.googlecode.instinct.internal.edge.org.hamcrest;

public interface MatcherDescriberBuilder extends MatcherDescriber {
    MatcherDescriberBuilder setReason(String reason);
    MatcherDescriberBuilder setExpectedLabelName(String labelName);
    MatcherDescriberBuilder setExpectedValue(String labelValue);
    MatcherDescriberBuilder setReturnedLabelName(String labelName);
    MatcherDescriberBuilder setReturnedValue(String returnedValue);
    MatcherDescriberBuilder addValue(String string);
    MatcherDescriberBuilder addSpace();
    MatcherDescriberBuilder addSpace(int spaces);
    MatcherDescriberBuilder addTab();
    MatcherDescriberBuilder addColon();
    MatcherDescriberBuilder addDash();
    MatcherDescriberBuilder addEquals();
    MatcherDescriberBuilder addUnderscore();
    MatcherDescriberBuilder addNewLine();
}
