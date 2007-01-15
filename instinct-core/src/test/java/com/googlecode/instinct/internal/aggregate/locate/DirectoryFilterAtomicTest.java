package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class DirectoryFilterAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(DirectoryFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(true);
        checkAccept(false);
    }

    private void checkAccept(final boolean value) {
        final File pathname = mock(File.class);
        expects(pathname).method("isDirectory").will(returnValue(value));
        assertEquals(value, new DirectoryFilter().accept(pathname));
    }
}
