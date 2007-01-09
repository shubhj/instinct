package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.test.InstinctTestCase;

public final class JavaClassNameImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        final File classesRoot = new File("somepath");
        final File classFilePath = new File("somepath");
        final JavaClassName n = new JavaClassNameImpl(classesRoot, classFilePath);
    }
}
