package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class DirectoryFilterAtomicTest extends InstinctTestCase {
    private File pathname;
    private FileFilter filter;

    public void testProperties() {
        checkClass(DirectoryFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(true);
        checkAccept(false);
    }

    private void checkAccept(final boolean value) {
        expects(pathname).method("isDirectory").will(returnValue(value));
        assertEquals(value, filter.accept(pathname));
    }

    @Override
    public void setUpTestDoubles() {
        pathname = mock(File.class);
    }

    @Override
    public void setUpSubject() {
        filter = new DirectoryFilter();
    }
}
