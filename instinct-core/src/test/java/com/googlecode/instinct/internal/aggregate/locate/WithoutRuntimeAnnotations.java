package com.googlecode.instinct.internal.aggregate.locate;

public class WithoutRuntimeAnnotations {
    private final String string1;

    public WithoutRuntimeAnnotations(final String string1) {
        this.string1 = string1;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getString1() {
        return string1;
    }
}
