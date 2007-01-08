package au.id.adams.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import au.id.adams.instinct.internal.util.ClassName;
import au.id.adams.instinct.internal.util.ClassNameImpl;
import au.id.adams.instinct.internal.util.Suggest;
import static au.id.adams.instinct.internal.util.ParamChecker.checkNotNull;

public final class ClassLocatorImpl implements ClassLocator {
    private final Comparator<File> comparator = new FileComparator();

    public ClassName[] locate(final File root, final FileFilter filter) {
        checkNotNull(root, filter);
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

    private ClassName[] toClasses(final File root, final File[] files) {
        final ClassName[] result = new ClassName[files.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ClassNameImpl(root, files[i]);
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
            throw new IllegalArgumentException(path + " " + "does not exist");
        }
        if (!path.isDirectory()) {
            throw new IllegalArgumentException(path + " " + "must be a directory.");
        }
    }
}
