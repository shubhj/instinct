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

package com.googlecode.instinct.internal.util.instance;

import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.JavaClassNameFactory;
import com.googlecode.instinct.internal.util.JavaClassNameFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.io.File;

public final class ClassInstantiatorImpl implements ClassInstantiator {
    private final JavaClassNameFactory classNameFactory = new JavaClassNameFactoryImpl();
    private final ClassEdge classEdge;

    public ClassInstantiatorImpl() {
        classEdge = new ClassEdgeImpl();
    }

    public ClassInstantiatorImpl(final ClassLoader classloader) {
        checkNotNull(classloader);
        classEdge = new ClassEdgeImpl(classloader);
    }

    public Class<?> instantiateClass(final File classFile, final File packageRoot) {
        checkNotNull(classFile, packageRoot);
        final JavaClassName className = classNameFactory.create(packageRoot, classFile);
        return classEdge.forName(className.getFullyQualifiedName());
    }

    public Class<?> instantiateClass(final String className) {
        checkNotNull(className);
        return classEdge.forName(className);
    }
}
