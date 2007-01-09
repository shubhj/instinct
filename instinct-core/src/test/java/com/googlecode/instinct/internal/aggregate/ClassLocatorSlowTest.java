package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.ClassName;
import com.googlecode.instinct.test.InstinctTestCase;

public final class ClassLocatorSlowTest extends InstinctTestCase {
    private static final int EXPECTED_CONTEXTS = 14;
    private PackageRootFinder packageRootFinder;
    private ClassLocator locator;

    public void testFindsCorrectNumberOfContexts() {
        final FileFilter filter = new AnnotationFileFilter(getSpecPackageRoot(), BehaviourContext.class);
        final ClassName[] names = locator.locate(getSpecPackageRoot(), filter);
        assertEquals(EXPECTED_CONTEXTS, names.length);
    }

    private File getSpecPackageRoot() {
        return new File(packageRootFinder.getPackageRoot(ClassLocatorSlowTest.class));
    }

    @Override
    public void setUpSubjects() {
        packageRootFinder = new PackageRootFinderImpl();
        locator = new ClassLocatorImpl();
    }
}
