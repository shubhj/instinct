package com.googlecode.instinct.internal.util;

import java.io.File;

public interface ClassInstantiatorFactory {
    ClassInstantiator create(File packageRoot);
}
