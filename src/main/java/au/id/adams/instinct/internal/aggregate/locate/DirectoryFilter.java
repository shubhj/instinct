package au.id.adams.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;

final class DirectoryFilter implements FileFilter {
    public boolean accept(final File pathname) {
        return pathname.isDirectory();
    }
}
