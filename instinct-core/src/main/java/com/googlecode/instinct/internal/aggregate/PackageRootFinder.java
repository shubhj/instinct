package com.googlecode.instinct.internal.aggregate;

public interface PackageRootFinder {
    <T> String getPackageRoot(Class<T> classToFindRootOf);
}
