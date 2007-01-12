package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.ReflectUtil.insertFieldValue;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    private AnnotationChecker checker;
    private File packageRoot;
    private File classFile;
    private EdgeClass edgeClass;

    public void testProperties() {
        checkClass(AnnotationCheckerImpl.class, AnnotationChecker.class);
    }

    public void testIsAnnotated() {
//        checker.isAnnotated(classFile, Annotation.class);
    }

    @Override
    public void setUpMocks() {
        packageRoot = mock(File.class);
        classFile = mock(File.class);
        edgeClass = mock(EdgeClass.class);
    }

    @Override
    public void setUpSubject() {
        checker = new AnnotationCheckerImpl(packageRoot);
        insertFieldValue(checker, "edgeClass", edgeClass);
    }
}
