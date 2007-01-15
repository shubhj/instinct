package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class DirectoryFilter implements FileFilter {
    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        return pathname.isDirectory();
    }
}
