package com.googlecode.instinct.internal.util;

import java.io.File;
import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class JavaClassNameFactoryImplAtomicTest extends InstinctTestCase {
    private File classesRoot;
    private File fileDir;
    private JavaClassNameFactory factory;

    public void testProperties() {
        checkClass(JavaClassNameFactoryImpl.class, JavaClassNameFactory.class);
    }

    public void testCreate() {
        final JavaClassName name = factory.create(classesRoot, fileDir);
        assertNotNull(name);
        assertEquals(JavaClassNameImpl.class, name.getClass());
    }

    @Override
    public void setUpTestDoubles() {
        classesRoot = mock(File.class);
        fileDir = mock(File.class);
    }

    @Override
    public void setUpSubject() {
        factory = new JavaClassNameFactoryImpl();
    }
}
