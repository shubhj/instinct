package com.googlecode.instinct.internal.util;

import java.io.File;

public final class DodgyClassNameImpl implements DodgyClassName {
    private final String fullyQualifiedClassName;

    public DodgyClassNameImpl(final File rootDir, final File clsFile) {
        fullyQualifiedClassName = getFullyQualifiedClassName(rootDir, clsFile);
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedClassName;
    }

    @Override
    public String toString() {
        return fullyQualifiedClassName;
    }

    private String getFullyQualifiedClassName(final File rootDir, final File clsFile) {
        final String rootAbsolute = rootDir.getAbsolutePath();
        final String clsAbsolute = clsFile.getAbsolutePath();
        final int length = rootAbsolute.length();
        final String path = clsAbsolute.substring(length);
        final String dotsForSlashes = path.replaceAll("[/\\\\]", ".");
        final String noLeadingDot = dotsForSlashes.substring(1);
        return noLeadingDot.replaceAll(".class", "");
    }
}
