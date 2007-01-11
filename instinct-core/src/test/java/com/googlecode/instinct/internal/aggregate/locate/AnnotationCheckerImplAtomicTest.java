package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.TestingException;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class AnnotationCheckerImplAtomicTest extends InstinctTestCase {
    private AnnotationChecker checker;
    private File packageRoot;
    private File classFile;

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
    }

    @Override
    public void setUpSubject() {
        checker = new AnnotationCheckerImpl(packageRoot);
        insertFieldValue(checker.getClass(), "edgeClass");
    }

    private <T> void insertFieldValue(final Class<T> cls, final String fieldName) {
        try {
            cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new TestingException(e);
        }
    }
}
