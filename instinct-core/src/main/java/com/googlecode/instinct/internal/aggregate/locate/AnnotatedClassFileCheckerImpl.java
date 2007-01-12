package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.lang.annotation.Annotation;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorFactory;
import com.googlecode.instinct.internal.util.ClassInstantiatorFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotatedClassFileCheckerImpl implements AnnotatedClassFileChecker {
    private AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
    private ClassInstantiatorFactory instantiatorFactory = new ClassInstantiatorFactoryImpl();
    private final File packageRoot;

    public AnnotatedClassFileCheckerImpl(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    public <A extends Annotation> boolean isAnnotated(final File classFile, final Class<A> annotationType) {
        checkNotNull(classFile, annotationType);
        final ClassInstantiator instantiator = instantiatorFactory.create(packageRoot);
        final Class<?> candidateClass = instantiator.instantiateClass(classFile);
        return annotationChecker.isAnnotated(candidateClass, annotationType);
    }
}
