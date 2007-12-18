package com.googlecode.instinct.internal.trait.param;

public interface ConstructorEmptyStringChecker {
    <T> void checkPublicConstructorsRejectEmptyString(Class<T> classToCheck);
}
