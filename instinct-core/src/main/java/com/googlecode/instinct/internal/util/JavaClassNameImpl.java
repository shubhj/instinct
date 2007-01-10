package com.googlecode.instinct.internal.util;

import java.io.File;

public final class JavaClassNameImpl implements JavaClassName {
    private final File classesRoot;
    private final File classFile;

    public JavaClassNameImpl(final File classesRoot, final File classFile) {
        this.classesRoot = classesRoot;
        this.classFile = classFile;
    }

    public String getFullyQualifiedName() {
        final String relativeClassPath = classFile.getAbsolutePath().replace(classesRoot.getAbsolutePath(), "");
        final String dotsForSlashes = relativeClassPath.replaceAll("[/\\\\]", ".");
        final String noLeadingDot = dotsForSlashes.substring(1);
        return noLeadingDot.replace(".class", "");
    }
}
