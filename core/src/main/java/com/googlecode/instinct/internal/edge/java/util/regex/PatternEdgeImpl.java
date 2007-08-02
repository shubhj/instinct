package com.googlecode.instinct.internal.edge.java.util.regex;

import java.util.regex.Pattern;

public final class PatternEdgeImpl implements PatternEdge {
    public Pattern compile(final String regex) {
        return Pattern.compile(regex);
    }
}
