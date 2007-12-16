package com.googlecode.instinct.internal.util.param;

public interface ConstructorEmptyStringChecker {
    <T> void checkPublicConstructorsRejectEmptyString(Class<T> classToCheck);
}
