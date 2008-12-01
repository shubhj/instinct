package com.googlecode.instinct.runner;

public enum ExitCode {
    SUCCESS(0), ERROR_NO_ARGUMENTS(1), TEST_FAILURES(2);
    private final int code;

    ExitCode(final int code) {
        this.code = code;
    }

    public static ExitCode fromPrimitive(final int code) {
        for (final ExitCode exitCode : ExitCode.values()) {
            if (exitCode.getCode() == code) {
                return exitCode;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
