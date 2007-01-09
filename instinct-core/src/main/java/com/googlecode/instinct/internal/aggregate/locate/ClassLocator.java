package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.instinct.internal.util.DodgyClassName;

public interface ClassLocator {
    DodgyClassName[] locate(File root, final FileFilter filter);
}
