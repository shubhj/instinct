package com.googlecode.instinct.internal.util;

import java.io.File;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ClassInstantiatorImpl implements ClassInstantiator {
    private JavaClassNameFactory classNameFactory = new JavaClassNameFactoryImpl();
    private EdgeClass edgeClass = new DefaultEdgeClass();
    private final File packageRoot;

    public ClassInstantiatorImpl(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    public Class<?> instantiateClass(final File classFile) {
        checkNotNull(classFile);
        final JavaClassName className = classNameFactory.create(packageRoot, classFile);
        return edgeClass.forName(className.getFullyQualifiedName());
    }
}
