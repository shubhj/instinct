package com.googlecode.instinct.internal.util;

import java.io.File;

public final class JavaClassNameImpl implements JavaClassName {
    private final File classesRoot;
    private final File classFile;

    @Suggest("Null check this class")
    public JavaClassNameImpl(final File classesRoot, final File classFile) {
        this.classesRoot = classesRoot;
        this.classFile = classFile;
    }

    public String getFullyQualifiedName() {
        final String relativeClassPath = classFile.getAbsolutePath().substring(classesRoot.getAbsolutePath().length());
        final String dotsForSlashes = relativeClassPath.replaceAll("[/\\\\]", ".");
        final String noLeadingDot = dotsForSlashes.startsWith(".") ? dotsForSlashes.substring(1) : dotsForSlashes;
        return noLeadingDot.replaceAll("\\.class$", "");
    }
}
