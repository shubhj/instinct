package com.googlecode.instinct.internal.util;

import java.io.File;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class JavaClassNameFactoryImpl implements JavaClassNameFactory {
    public JavaClassName create(final File classesRoot, final File classFile) {
        checkNotNull(classesRoot, classFile);
        return new JavaClassNameImpl(classesRoot, classFile);
    }
}
