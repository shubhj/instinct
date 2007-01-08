package au.id.adams.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotationFileFilter implements FileFilter {
    private final Class<? extends Annotation> annotationType;
    private final AnnotationChecker checker;

    public <T extends Annotation> AnnotationFileFilter(final File packageRoot, final Class<T> annotationType) {
        checkNotNull(packageRoot, annotationType);
        this.annotationType = annotationType;
        checker = new AnnotationCheckerImpl(packageRoot);

    }

    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        return !pathname.isDirectory() && checker.isAnnotated(pathname, annotationType);
    }
}
