package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class JavaClassNameImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(JavaClassNameImpl.class, JavaClassName.class);
    }

    public void testGetFullyQualifiedClassName() {
        checkGetFullyQualifiedClassName("/home/me/projects/src/", "/home/me/projects/src/com/foo/Bar.class", "com.foo.Bar");
        checkGetFullyQualifiedClassName("/home/me/projects/src", "/home/me/projects/src/com/foo/Bar.class", "com.foo.Bar");
        checkGetFullyQualifiedClassName("/home/me/projects/src/", "/home/me/projects/src/com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("/home/me/projects/src", "/home/me/projects/src/com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("/", "/com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("", "com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("\\", "\\com\\foo\\bar\\Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("/", "/com/foo/bar/class/Baz.class", "com.foo.bar.class.Baz");
    }

    private void checkGetFullyQualifiedClassName(final String classesRootPath, final String classFilePath,
            final String expectedFullyQualifiedClassName) {
        final JavaClassName className = new JavaClassNameImpl(new File(classesRootPath), new File(classFilePath));
        assertEquals(expectedFullyQualifiedClassName, className.getFullyQualifiedName());
    }
}
