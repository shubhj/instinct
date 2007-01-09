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

    @Suggest("Pull this logic out into another class - PackageRootFinder?")
    private File getSpecPackageRoot() {
        final String classResource = classInSpecTree.getName().replace('.', '/') + ".class";
        final URL classFileUrl = classInSpecTree.getResource('/' + classResource);
        final File newPackageRoot = new File(classFileUrl.getFile().replace(classResource, ""));
//        System.out.println("packageRoot = " + newPackageRoot);
//        final URL resource = classInSpecTree.getResource(PATH_ROOT);
//        final String specRoot = resource.getFile();
//        final File oldPackageRoot = new File(specRoot);
//        System.out.println("oldPackageRoot = " + oldPackageRoot);
//        return oldPackageRoot;
        return newPackageRoot;
    }
}
