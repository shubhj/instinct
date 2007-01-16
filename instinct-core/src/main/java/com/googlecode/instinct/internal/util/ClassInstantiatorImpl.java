/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
