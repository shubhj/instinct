package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.checker.ClassChecker;

public final class PackageRootFinderImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        ClassChecker.checkClass(PackageRootFinderImpl.class, PackageRootFinder.class);
    }
}
