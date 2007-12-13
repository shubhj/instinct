/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
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
