package com.googlecode.instinct.internal.util.param;

public interface ConstructorNullParameterTestChecker {
    <T> void checkPublicConstructorsRejectNull(Class<T> classToCheck);
}
