package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.test.InstinctTestCase;

public final class JavaClassNameImplAtomicTest extends InstinctTestCase {
    public void testGetFullyQualifiedClassName() {
        final File classesRoot = new File("/home/me/projects/src/");
        final File classFilePath = new File("/home/me/projects/src/com/foo/Bar.class");
        final JavaClassName n = new JavaClassNameImpl(classesRoot, classFilePath);
        final String fqcn = n.getFullyQualifiedName();
        assertEquals("com.foo.Bar", fqcn);
    }
}
