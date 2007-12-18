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

package com.googlecode.instinct.internal.locate.cls;

import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;
import com.googlecode.instinct.marker.AnnotationAttribute;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import java.io.File;
import java.io.FileFilter;
import java.util.Set;

@Suggest({"Move to public API.", "Expose a package root dir also, keep it as a field."})
@SuppressWarnings({"OverlyCoupledClass"})
public final class ContextFinderImpl implements ContextFinder {
    private PackageRootFinder packageRootFinder = new PackageRootFinderImpl();
    private ClassLocator classLocator = new ClassLocatorImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final Class<?> classInSpecTree;

    public <T> ContextFinderImpl(final Class<T> classInSpecTree) {
        checkNotNull(classInSpecTree);
        this.classInSpecTree = classInSpecTree;
    }

    @Fix({"Return a set here.", "Return either classes or contexts, or specifications, consider removing contexts as an entity in Instinct"})
    public JavaClassName[] getContextNames(final String... specificationGroups) {
        final File packageRoot = objectFactory.create(File.class, packageRootFinder.getPackageRoot(classInSpecTree));
        final AnnotationAttribute attributeConstraint = new AnnotationAttribute("group", specificationGroups);
        final FileFilter annotatedClasses = createMarkedClassFileFilter(packageRoot, attributeConstraint);
        final FileFilter markedMethodClasses = createMarkedMethodsFilter(packageRoot, attributeConstraint);
        final Set<JavaClassName> names = classLocator.locate(packageRoot, annotatedClasses, markedMethodClasses);
        return names.toArray(new JavaClassName[names.size()]);
    }

    private FileFilter createMarkedClassFileFilter(final File packageRoot, final AnnotationAttribute attributeConstraint) {
        final MarkingScheme markingScheme = createContextMarkingScheme(attributeConstraint);
        return objectFactory.create(ClassWithContextAnnotationFileFilter.class, packageRoot, markingScheme);
    }

    private FileFilter createMarkedMethodsFilter(final File packageRoot, final AnnotationAttribute attributeConstraint) {
        final MarkingScheme markingScheme = createSpecificationMarkingScheme(attributeConstraint);
        return objectFactory.create(ClassWithMarkedMethodsFileFilter.class, packageRoot, markingScheme);
    }

    private MarkingScheme createContextMarkingScheme(final AnnotationAttribute attributeConstraint) {
        final NamingConvention namingConvention = objectFactory.create(ContextNamingConvention.class);
        return objectFactory.create(MarkingSchemeImpl.class, Context.class, namingConvention, attributeConstraint);
    }

    private MarkingScheme createSpecificationMarkingScheme(final AnnotationAttribute attributeConstraint) {
        final NamingConvention namingConvention = objectFactory.create(SpecificationNamingConvention.class);
        return objectFactory.create(MarkingSchemeImpl.class, Specification.class, namingConvention, attributeConstraint);
    }
}
