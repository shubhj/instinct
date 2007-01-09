package com.googlecode.instinct.internal.util;

import java.io.File;

public final class ClassNameImpl implements ClassName {
    private final String fullyQualifiedClassName;

    public ClassNameImpl(final File rootDir, final File clsFile) {
        final String rootAbsolute = rootDir.getAbsolutePath();
        final String clsAbsolute = clsFile.getAbsolutePath();
        final String path = getPathRelativeToRoot(rootAbsolute, clsAbsolute);
        fullyQualifiedClassName = convertClassFilePathToClassName(path);
    }

    private String getPathRelativeToRoot(final String rootAbsolute, final String absolute) {
        final int length = rootAbsolute.length();
        return absolute.substring(length);
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedClassName;
    }

    @Suggest("Appears to be broken for some cases of rootDir & clsFile (empty or /)")
    private String convertClassFilePathToClassName(final String path) {
        final String dotsForSlashes = path.replaceAll("[/\\\\]", ".");
        final String noLeadingDot = dotsForSlashes.substring(1);
        return noLeadingDot.replaceAll(".class", "");
    }
}
