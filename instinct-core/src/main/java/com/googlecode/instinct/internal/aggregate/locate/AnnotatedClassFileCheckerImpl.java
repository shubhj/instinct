package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.lang.annotation.Annotation;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.JavaClassNameFactory;
import com.googlecode.instinct.internal.util.JavaClassNameFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class AnnotatedClassFileCheckerImpl implements AnnotatedClassFileChecker {
    private final File packageRoot;
    private EdgeClass edgeClass = new DefaultEdgeClass();
    private JavaClassNameFactory classNameFactory = new JavaClassNameFactoryImpl();

    public AnnotatedClassFileCheckerImpl(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    public <T extends Annotation> boolean isAnnotated(final File classFile, final Class<T> annotationType) {
        checkNotNull(classFile, annotationType);
        final Class<T> candidateClass = instantiateClass(classFile);
        return isAnnotated(candidateClass, annotationType);
    }

    @SuppressWarnings({"unchecked"})
    private <T> Class<T> instantiateClass(final File classFile) {
        final JavaClassName className = classNameFactory.create(packageRoot, classFile);
        final String fullyQualifiedName = className.getFullyQualifiedName();
        return (Class<T>) edgeClass.forName(fullyQualifiedName);
    }

    private <T extends Annotation> boolean isAnnotated(final Class<T> candidateClass, final Class<T> annotationType) {
        return candidateClass.isAnnotationPresent(annotationType);
    }
}
