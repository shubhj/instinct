package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;

public interface AnnotatedClassFileCheckerFactory {
    AnnotatedClassFileChecker create(File packageRoot);
}
