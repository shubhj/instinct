package com.googlecode.instinct.internal.util.boost;

public interface ConstructorEmptyStringChecker {
    <T> void checkPublicConstructorsRejectEmptyString(Class<T> classToCheck);
}
