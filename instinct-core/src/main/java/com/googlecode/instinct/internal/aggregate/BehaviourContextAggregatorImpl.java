package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.ClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class BehaviourContextAggregatorImpl implements BehaviourContextAggregator {
    private static final Class<? extends Annotation> ANNOTATION_TO_FIND = BehaviourContext.class;
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

    @Suggest("Pull this logic out into another class - PackageRootFinder?")
    private File getSpecPackageRoot() {
        final String classResourceNoLeadingSlash = classInSpecTree.getName().replace('.', '/') + ".class";
        final URL classResourceUrl = classInSpecTree.getResource('/' + classResourceNoLeadingSlash);
        final String packageRootPath = classResourceUrl.getFile().replace(classResourceNoLeadingSlash, "");
        return new File(packageRootPath);
    }
}
