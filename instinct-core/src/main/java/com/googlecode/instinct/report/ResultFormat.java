package com.googlecode.instinct.report;

import com.googlecode.instinct.internal.report.BriefResultMessageBuilder;
import com.googlecode.instinct.internal.report.VerboseResultMessageBuilder;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Figure out how to test this sucker.")
public enum ResultFormat {
    BRIEF {
        @Override
        public ResultMessageBuilder getMessageBuilder() {
            return new BriefResultMessageBuilder();
        }
    },

    VERBOSE {
        @Override
        public ResultMessageBuilder getMessageBuilder() {
            return new VerboseResultMessageBuilder();
        }
    };

    // SUPPRESS IllegalToken {
    public abstract ResultMessageBuilder getMessageBuilder();
    // } SUPPRESS IllegalToken
}
