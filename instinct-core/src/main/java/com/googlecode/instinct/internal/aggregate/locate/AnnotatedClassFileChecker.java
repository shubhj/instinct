package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.lang.annotation.Annotation;

public interface AnnotatedClassFileChecker {
    <T extends Annotation> boolean isAnnotated(final File classFile, final Class<T> annotationType);
}
