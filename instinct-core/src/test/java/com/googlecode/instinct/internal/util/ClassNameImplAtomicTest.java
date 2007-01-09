package com.googlecode.instinct.internal.util;

import java.io.File;
import com.googlecode.instinct.test.InstinctTestCase;


public final class ClassNameImplAtomicTest extends InstinctTestCase {
    public void testThisIsAStupidTest() {
        final ClassName className = new ClassNameImpl(new File("/"), new File("/com/googlecode/instinct/Foo.class"));
        assertEquals("om.googlecode.instinct.Foo", className.getFullyQualifiedName());
    }
}
