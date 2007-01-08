package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.util.ClassName;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextAggregatorImpl implements BehaviourContextAggregator {
    private static final Class<? extends Annotation> ANNOTATION_TO_FIND = BehaviourContext.class;
    private static final String PATH_ROOT = "/";
    private final Class<?> classInSpecTree;
    private final ClassLocator locator;

    public <T> BehaviourContextAggregatorImpl(final Class<T> classInSpecTree, final ClassLocator locator) {
        checkNotNull(classInSpecTree, locator);
        this.classInSpecTree = classInSpecTree;
        this.locator = locator;
    }

    public ClassName[] getContexts() {
        final File packageRoot = getSpecPackageRoot();
        return locator.locate(packageRoot, new AnnotationFileFilter(packageRoot, ANNOTATION_TO_FIND));
    }

    @Suggest("May need to do better than just using the class.")
    private File getSpecPackageRoot() {
        final URL resource = classInSpecTree.getResource(PATH_ROOT);
        final String specRoot = resource.getFile();
        return new File(specRoot);
    }
}
