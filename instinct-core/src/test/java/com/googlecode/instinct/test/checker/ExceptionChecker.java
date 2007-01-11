package com.googlecode.instinct.test.checker;

import static com.googlecode.instinct.test.checker.ClassChecker.checkPropertiesSuperClass;

@SuppressWarnings({"ExceptionClassNameDoesntEndWithException"})
public final class ExceptionChecker {
    private ExceptionChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T extends RuntimeException> void checkException(final Class<T> exceptionClass) {
        checkPropertiesSuperClass(RuntimeException.class, exceptionClass);
    }
}
