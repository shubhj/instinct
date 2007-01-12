package com.googlecode.instinct.internal.util;

import java.io.File;

public interface JavaClassNameFactory {
    JavaClassName create(File classesRoot, File classFile);
}
