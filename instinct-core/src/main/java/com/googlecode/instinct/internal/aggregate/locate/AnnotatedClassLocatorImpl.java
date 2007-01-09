package com.googlecode.instinct.internal.aggregate.locate;

import com.googlecode.instinct.internal.util.DodgyClassName;
import com.googlecode.instinct.internal.util.DodgyClassNameImpl;
import com.googlecode.instinct.internal.util.Suggest;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class AnnotatedClassLocatorImpl implements AnnotatedClassLocator {
    private final Comparator<File> comparator = new FileComparator();

    @Suggest("Perhaps the filter is the param that should be passed, rather than the annotation. Could then make this a filtered class locator.")
    public DodgyClassName[] locate(final File root, final FileFilter filter) {
        final File[] files = sortedDeepLocate(root, filter);
        return toClasses(root, files);
    }

    private File[] sortedDeepLocate(final File root, final FileFilter filter) {
        final List<File> result = new ArrayList<File>();
        recursiveLocate(root, filter, result);
        sort(result);
        return result.toArray(new File[]{});
    }

    private void recursiveLocate(final File searchBase, final FileFilter filter, final List<File> result) {
        ensureDir(searchBase);
        final File[] subdirs = getSubdirectories(searchBase);
        for (final File subdir : subdirs) {
            recursiveLocate(subdir, filter, result);
        }
        findMatchingClasses(searchBase, filter, result);
    }

    @Suggest("This is very similar")
    private File[] getSubdirectories(final File dir) {
        final FileFilter filter = new DirectoryFilter();
        return dir.listFiles(filter);
    }

    private DodgyClassName[] toClasses(final File root, final File[] files) {
        final DodgyClassName[] result = new DodgyClassName[files.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new DodgyClassNameImpl(root, files[i]);
        }
        return result;
    }

    private void findMatchingClasses(final File dir, final FileFilter filter, final List<File> result) {
        final List<File> list = asList(dir.listFiles(filter));
        result.addAll(list);
    }

    private void sort(final List<File> files) {
        Collections.sort(files, comparator);
    }

    private void ensureDir(final File path) {
        if (!path.exists()) {
            throw new IllegalStateException(path + " " + "does not exist");
        }
        if (!path.isDirectory()) {
            throw new IllegalStateException(path + " " + "must be a directory.");
        }
    }
}
