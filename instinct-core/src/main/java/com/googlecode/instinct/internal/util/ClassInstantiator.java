package com.googlecode.instinct.internal.util;

import java.io.File;

public interface ClassInstantiator {
    Class<?> instantiateClass(File classFile);
}
