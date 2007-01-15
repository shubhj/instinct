package com.googlecode.instinct.internal.aggregate.locate;

public class WithoutRuntimeAnnotations {
    private final String string;

    public WithoutRuntimeAnnotations(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getString() {
        return string;
    }
}
