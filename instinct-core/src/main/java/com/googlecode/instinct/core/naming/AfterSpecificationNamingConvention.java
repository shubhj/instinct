package com.googlecode.instinct.core.naming;

public final class AfterSpecificationNamingConvention implements NamingConvention {
    public String getPattern() {
        return "^tearDown";
    }
}
