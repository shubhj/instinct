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

package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.instinct.internal.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.locate.ClassLocator;
import com.googlecode.instinct.internal.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.Context;

public final class AnnotatedContextAggregatorImpl implements ContextAggregator {
    private PackageRootFinder packageRootFinder = new PackageRootFinderImpl();
    private ClassLocator classLocator = new ClassLocatorImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final Class<?> classInSpecTree;

    public <T> AnnotatedContextAggregatorImpl(final Class<T> classInSpecTree) {
        checkNotNull(classInSpecTree);
        this.classInSpecTree = classInSpecTree;
    }

    public JavaClassName[] getContextNames() {
        final File packageRoot = objectFactory.create(File.class, packageRootFinder.getPackageRoot(classInSpecTree));
        final FileFilter filter = objectFactory.create(AnnotationFileFilter.class, packageRoot, Context.class);
        return classLocator.locate(packageRoot, filter);
    }
}
