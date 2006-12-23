package au.id.adams.instinct.internal.aggregate.locate;

import java.io.File;
import java.lang.annotation.Annotation;

interface AnnotationChecker {
    <T extends Annotation> boolean isAnnotated(final File classFile, final Class<T> annotationType);
}
