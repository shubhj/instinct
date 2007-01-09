package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.test.InstinctTestCase;

public final class JavaClassNameImplAtomicTest extends InstinctTestCase {
    public void testGetFullyQualifiedClassName() {
        checkGetFullyQualifiedClassName("/home/me/projects/src/", "/home/me/projects/src/com/foo/Bar.class", "com.foo.Bar");
    }

    private void checkGetFullyQualifiedClassName(final String classesRootPath, final String classFilePath,
            final String expectedFullyQualifiedClassName) {
        final JavaClassName className = new JavaClassNameImpl(new File(classesRootPath), new File(classFilePath));
        assertEquals(expectedFullyQualifiedClassName, className.getFullyQualifiedName());
    }
}
