package com.googlecode.instinct.internal.aggregate;

import java.net.URL;

public final class PackageRootFinderImpl implements PackageRootFinder {
    public <T> String getPackageRoot(final Class<T> classToFindRootOf) {
        final String classResourceNoLeadingSlash = classToFindRootOf.getName().replace('.', '/') + ".class";
        final URL classResourceUrl = classToFindRootOf.getResource('/' + classResourceNoLeadingSlash);
        return classResourceUrl.getFile().replace(classResourceNoLeadingSlash, "");
    }
}
