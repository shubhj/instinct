package com.googlecode.instinct.internal.util;

import java.io.File;
import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class ClassInstantiatorFactoryImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(ClassInstantiatorFactoryImpl.class, ClassInstantiatorFactory.class);
    }

    public void testCreate() {
        final ClassInstantiator instantiator = new ClassInstantiatorFactoryImpl().create(mock(File.class));
        assertNotNull(instantiator);
        assertEquals(ClassInstantiatorImpl.class, instantiator.getClass());
    }
}
