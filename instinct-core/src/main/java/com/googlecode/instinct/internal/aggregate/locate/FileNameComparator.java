package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.util.Comparator;

final class FileNameComparator implements Comparator<File> {
    public int compare(final File o1, final File o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
