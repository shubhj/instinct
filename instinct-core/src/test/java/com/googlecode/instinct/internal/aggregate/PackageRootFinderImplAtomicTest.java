package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class PackageRootFinderImplAtomicTest extends InstinctTestCase {
    public void testProperties() {
        checkClass(PackageRootFinderImpl.class, PackageRootFinder.class);
    }
}
