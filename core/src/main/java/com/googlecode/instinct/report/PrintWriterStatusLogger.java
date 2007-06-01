package com.googlecode.instinct.report;

import java.io.PrintWriter;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class PrintWriterStatusLogger implements StatusLogger {
    private final PrintWriter writer;

    public PrintWriterStatusLogger(final PrintWriter writer) {
        checkNotNull(writer);
        this.writer = writer;
    }

    public void log(final String message) {
        checkNotNull(message);
        writer.println(message);
    }
}
