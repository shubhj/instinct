package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;

public final class AnnotatedClassFileCheckerFactoryImpl implements AnnotatedClassFileCheckerFactory {
    public AnnotatedClassFileChecker create(final File packageRoot) {
        return new AnnotatedClassFileCheckerImpl(packageRoot);
    }
}
