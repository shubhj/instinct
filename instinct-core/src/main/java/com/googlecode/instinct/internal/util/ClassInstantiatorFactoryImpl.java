package com.googlecode.instinct.internal.util;

import java.io.File;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ClassInstantiatorFactoryImpl implements ClassInstantiatorFactory {
    public ClassInstantiator create(final File packageRoot) {
        checkNotNull(packageRoot);
        return new ClassInstantiatorImpl(packageRoot);
    }
}
