package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class AnnotationFileFilterAtomicTest extends InstinctTestCase {
    private FileFilter filter;
    private File packageRoot;
    private File pathname;
    private AnnotatedClassFileCheckerFactory checkerFactory;
    private AnnotatedClassFileChecker checker;

    public void testProperties() {
        checkClass(AnnotationFileFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(false, true, true);
        checkAccept(true, false, false);
    }

    private void checkAccept(final boolean pathIsADirectory, final boolean classHasAnnotation, final boolean isAnnotated) {
        expects(checkerFactory).method("create").with(same(packageRoot)).will(returnValue(checker));
        expects(pathname).method("isDirectory").will(returnValue(pathIsADirectory));
        if (!pathIsADirectory) {
            expects(checker).method("isAnnotated").with(same(pathname), same(BehaviourContext.class)).will(returnValue(classHasAnnotation));
        }
        final boolean accept = filter.accept(pathname);
        assertEquals(isAnnotated, accept);
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "mockPackageRoot");
        pathname = mock(File.class, "mockPathname");
        checkerFactory = mock(AnnotatedClassFileCheckerFactory.class);
        checker = mock(AnnotatedClassFileChecker.class);
    }

    @Override
    public void setUpSubject() {
        filter = new AnnotationFileFilter(packageRoot, BehaviourContext.class);
        insertFieldValue(filter, "checkerFactory", checkerFactory);
    }
}
