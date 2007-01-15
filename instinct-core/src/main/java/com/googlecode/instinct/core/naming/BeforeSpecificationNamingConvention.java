package com.googlecode.instinct.core.naming;

public final class BeforeSpecificationNamingConvention implements NamingConvention {
    public String getPattern() {
        return "^setUp";
    }
}
