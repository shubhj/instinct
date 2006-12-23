package au.id.adams.instinct.internal.util;

import java.io.File;

public final class ClassNameImpl implements ClassName {
    private final String fullyQualifiedClassName;

    public ClassNameImpl(final File rootDir, final File clsFile) {
        final String rootAbsolute = rootDir.getAbsolutePath();
        final String clsAbsolute = clsFile.getAbsolutePath();
        final String path = getPathRelativeToRoot(rootAbsolute, clsAbsolute);
        fullyQualifiedClassName = slashesToDots(path);
    }

    private String getPathRelativeToRoot(final String rootAbsolute, final String absolute) {
        final int length = rootAbsolute.length();
        return absolute.substring(length);
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedClassName;
    }

    @Suggest("DO we need to escape / as well?")
    private String slashesToDots(final String path) {
        final String deBillGates = path.replaceAll("[/\\\\]", ".");
        final String noLeadingSlash = deBillGates.substring(1);
        return noLeadingSlash.replaceAll(".class", "");
    }
}
