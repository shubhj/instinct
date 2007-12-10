package com.googlecode.instinct.internal.util.boost;

public interface ConstructorNullParameterTestChecker {
    <T> void checkPublicConstructorsRejectNull(Class<T> classToCheck);
}
