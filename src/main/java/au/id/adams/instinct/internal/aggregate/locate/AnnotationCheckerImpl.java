package au.id.adams.instinct.internal.aggregate.locate;

import java.io.File;
import java.lang.annotation.Annotation;
import au.id.adams.instinct.internal.util.ClassName;
import au.id.adams.instinct.internal.util.ClassNameImpl;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;

final class AnnotationCheckerImpl implements AnnotationChecker {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final File packageRoot;

    AnnotationCheckerImpl(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    public <T extends Annotation> boolean isAnnotated(final File classFile, final Class<T> annotationType) {
        checkNotNull(classFile, annotationType);
        final Class<T> annotationCandidate = instantiateClass(classFile);
        final Annotation annotation = annotationCandidate.getAnnotation(annotationType);
        return annotation != null;
    }

    @SuppressWarnings({"unchecked"})
    private <T> Class<T> instantiateClass(final File classFile) {
        final ClassName className = new ClassNameImpl(packageRoot, classFile);
        return (Class<T>) edgeClass.forName(className.getFullyQualifiedName());
    }
}
