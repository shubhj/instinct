package com.googlecode.instinct.internal.util;

import java.io.File;

public final class JavaClassNameImpl implements JavaClassName {
    public JavaClassNameImpl(final File classesRoot, final File classFilePath) {
    }

    public String getFullyQualifiedName() {
        return "com.foo.Bar";
    }
}
