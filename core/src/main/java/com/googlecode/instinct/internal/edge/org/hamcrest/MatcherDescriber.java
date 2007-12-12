/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

public interface MatcherDescriber {
    MatcherDescriber setReason(String reason);
    MatcherDescriber setExpectedLabelName(String labelName);
    MatcherDescriber setExpectedValue(String labelValue);
    MatcherDescriber setReturnedLabelName(String labelName);
    MatcherDescriber setReturnedValue(String returnedValue);
    MatcherDescriber addValue(String string);
    MatcherDescriber addSpace();
    MatcherDescriber addSpace(int spaces);
    MatcherDescriber addTab();
    MatcherDescriber addColon();
    MatcherDescriber addDash();
    MatcherDescriber addEquals();
    MatcherDescriber addUnderscore();
    MatcherDescriber addNewLine();
}
