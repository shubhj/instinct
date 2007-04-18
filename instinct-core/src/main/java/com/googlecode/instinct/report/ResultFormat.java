package com.googlecode.instinct.report;

import com.googlecode.instinct.internal.report.BriefContextResultMessageBuilder;
import com.googlecode.instinct.internal.report.VerboseContextResultMessageBuilder;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Figure out how to test this sucker.")
public enum ResultFormat {
    BRIEF {
        public ContextResultMessageBuilder getMessageBuilder() {
            return new BriefContextResultMessageBuilder();
        }
    },

    VERBOSE {
        public ContextResultMessageBuilder getMessageBuilder() {
            return new VerboseContextResultMessageBuilder();
        }
    };

    // SUPPRESS IllegalToken {
    public abstract ContextResultMessageBuilder getMessageBuilder();
    // } SUPPRESS IllegalToken
}
