package com.googlecode.instinct.internal.trait.param;

public interface ConstructorNullParameterTestChecker {
    <T> void checkPublicConstructorsRejectNull(Class<T> classToCheck);
}
