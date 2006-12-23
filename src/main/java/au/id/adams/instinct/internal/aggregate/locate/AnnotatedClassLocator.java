package au.id.adams.instinct.internal.aggregate.locate;

import java.io.File;
import java.io.FileFilter;
import au.id.adams.instinct.internal.util.ClassName;

public interface AnnotatedClassLocator {
    ClassName[] locate(File root, final FileFilter filter);
}
