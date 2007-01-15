package com.googlecode.instinct.internal.aggregate.locate;

import java.io.FileFilter;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class DirectoryFilterAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(DirectoryFilter.class, FileFilter.class);
    }
}
