package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class BehaviourContextAggregatorImpl implements BehaviourContextAggregator {
    private final PackageRootFinder finder = new PackageRootFinderImpl();
    private final Class<?> classInSpecTree;
    private final ClassLocator locator;

    public <T> BehaviourContextAggregatorImpl(final Class<T> classInSpecTree, final ClassLocator locator) {
        checkNotNull(classInSpecTree, locator);
        this.classInSpecTree = classInSpecTree;
        this.locator = locator;
    }

    public JavaClassName[] getContextNames() {
        final File packageRoot = new File(finder.getPackageRoot(classInSpecTree));
        return locator.locate(packageRoot, new AnnotationFileFilter(packageRoot, BehaviourContext.class));
    }
}
