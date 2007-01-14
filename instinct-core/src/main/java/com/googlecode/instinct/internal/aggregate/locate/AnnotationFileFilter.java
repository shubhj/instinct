package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;

public final class AnnotationFileFilter implements FileFilter {
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final Class<? extends Annotation> annotationType;
    private final File packageRoot;

    public <T extends Annotation> AnnotationFileFilter(final File packageRoot, final Class<T> annotationType) {
        checkNotNull(packageRoot, annotationType);
        this.annotationType = annotationType;
        this.packageRoot = packageRoot;
    }

    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        final AnnotatedClassFileChecker checker = objectFactory.create(AnnotatedClassFileCheckerImpl.class, packageRoot);
        return !pathname.isDirectory() && checker.isAnnotated(pathname, annotationType);
    }
}
