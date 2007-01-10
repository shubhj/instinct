package com.googlecode.instinct.internal.aggregate;

import java.net.URL;
import com.googlecode.instinct.internal.util.Suggest;

public final class PackageRootFinderImpl implements PackageRootFinder {
    @Suggest({"Move Class to a boundary (use Boost's)","Move resource loading into a ResourceLoader class"})
    public <T> String getPackageRoot(final Class<T> classToFindRootOf) {
        final String fqn = classToFindRootOf.getName();
        final String classResourceNoLeadingSlash = fqn.replace('.', '/') + ".class";
        final String classResourcePath = '/' + classResourceNoLeadingSlash;
        final URL classResourceUrl = classToFindRootOf.getResource(classResourcePath);
        final String absolutePath = classResourceUrl.getFile();
        return absolutePath.replace(classResourceNoLeadingSlash, "");
    }
}
