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
import java.util.Set;
import com.googlecode.instinct.internal.locate.ClassLocator;
import com.googlecode.instinct.internal.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.locate.ClassWithContextAnnotationFileFilter;
import com.googlecode.instinct.internal.locate.ClassWithMarkedMethodsFileFilter;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;

public final class ContextClassAggregatorImpl implements ContextAggregator {
    private PackageRootFinder packageRootFinder = new PackageRootFinderImpl();
    private ClassLocator classLocator = new ClassLocatorImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final Class<?> classInSpecTree;

    public <T> ContextClassAggregatorImpl(final Class<T> classInSpecTree) {
        checkNotNull(classInSpecTree);
        this.classInSpecTree = classInSpecTree;
    }

    @Fix({"Return a set here.", "Add the ClassWithMarkedMethodsFileFilterHereAlso"})
    public JavaClassName[] getContextNames() {
        final File packageRoot = objectFactory.create(File.class, packageRootFinder.getPackageRoot(classInSpecTree));

        final FileFilter filter = createMarkedClassFileFilter(packageRoot);
        final FileFilter filter2 = createMarkedMethodsFilter(packageRoot);
        final Set<JavaClassName> names = classLocator.locate(packageRoot, filter, filter2);
        return names.toArray(new JavaClassName[names.size()]);
    }

    private FileFilter createMarkedClassFileFilter(final File packageRoot) {
        final MarkingScheme markingScheme = createContextMarkingScheme();
        return objectFactory.create(ClassWithContextAnnotationFileFilter.class, packageRoot, markingScheme);
    }

    private FileFilter createMarkedMethodsFilter(final File packageRoot) {
        final MarkingScheme markingScheme = createSpecificationMarkingScheme();
        return objectFactory.create(ClassWithMarkedMethodsFileFilter.class, packageRoot, markingScheme);
    }

    private MarkingScheme createContextMarkingScheme() {
        final NamingConvention namingConvention = objectFactory.create(ContextNamingConvention.class);
        return objectFactory.create(MarkingSchemeImpl.class, Context.class, namingConvention);
    }

    private MarkingScheme createSpecificationMarkingScheme() {
        final NamingConvention namingConvention = objectFactory.create(SpecificationNamingConvention.class);
        return new MarkingSchemeImpl(Specification.class, namingConvention);
    }
}
