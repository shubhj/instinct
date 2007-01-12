package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotationFileFilter implements FileFilter {
    private final Class<? extends Annotation> annotationType;
    private final AnnotatedClassFileChecker checker;

    public <T extends Annotation> AnnotationFileFilter(final File packageRoot, final Class<T> annotationType) {
        checkNotNull(packageRoot, annotationType);
        this.annotationType = annotationType;
        checker = new AnnotatedClassFileCheckerImpl(packageRoot);

    }

    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        return !pathname.isDirectory() && checker.isAnnotated(pathname, annotationType);
    }
}
