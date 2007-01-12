package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import com.googlecode.instinct.mock.Mocker;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class AnnotatedClassFileCheckerFactoryImplAtomicTest extends InstinctTestCase {
    private File packageRoot;

    public void testProperties() {
        checkClass(AnnotatedClassFileCheckerFactoryImpl.class, AnnotatedClassFileCheckerFactory.class);
    }

    public void testCreate() {
        final AnnotatedClassFileChecker checker = new AnnotatedClassFileCheckerFactoryImpl().create(packageRoot);
        assertNotNull(checker);
        assertEquals(AnnotatedClassFileCheckerImpl.class, checker.getClass());
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = Mocker.mock(File.class, "mockPackageRoot");
    }
}
