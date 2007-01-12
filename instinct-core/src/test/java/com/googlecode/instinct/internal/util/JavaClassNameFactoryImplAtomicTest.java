package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkProperties;
import com.googlecode.instinct.mock.Mocker;

public final class JavaClassNameFactoryImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkProperties(JavaClassNameFactory.class, JavaClassNameFactoryImpl.class);
    }

    public void testCreate() {
        final File classesRoot = Mocker.mock(File.class);
        final File fileDir = Mocker.mock(File.class);
        final JavaClassName className = new JavaClassNameFactoryImpl().create(classesRoot, fileDir);
    }
}
