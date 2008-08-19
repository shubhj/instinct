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

package com.googlecode.instinct.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.locate.ContextFinder;
import com.googlecode.instinct.internal.locate.cls.PackageRootFinder;
import com.googlecode.instinct.internal.locate.cls.ClassLocator;
import com.googlecode.instinct.locate.ContextFinderImpl;
import com.googlecode.instinct.internal.locate.cls.ClassWithContextAnnotationFileFilter;
import com.googlecode.instinct.internal.locate.cls.ClassWithMarkedMethodsFileFilter;
import com.googlecode.instinct.marker.AnnotationAttribute;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.ALL_GROUPS;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;
import org.jmock.Expectations;

@SuppressWarnings({"serial", "ClassExtendsConcreteCollection", "AnonymousInnerClassWithTooManyMethods"})
public final class ContextFinderImplAtomicTest extends InstinctTestCase {
    private static final Class<?> CLASS_IN_SPEC_TREE = ContextFinderImplAtomicTest.class;
    private static final String PACKAGE_ROOT = "";
    @Subject(auto = false) private ContextFinder finder;
    @Mock private PackageRootFinder packageRootFinder;
    @Mock private ClassLocator classLocator;
    @Mock private ObjectFactory objectFactory;
    @Mock private File packageRoot;
    @Mock private FileFilter classFileFilter;
    @Dummy private Set<JavaClassName> classNames;
    @Dummy private MarkingScheme contextMarkingScheme;
    @Dummy private NamingConvention contextNamingConvention;
    @Dummy private NamingConvention specificationNamingConvention;
    @Dummy private MarkingScheme specificationMarkingScheme;
    @Dummy private FileFilter methodFileFilter;
    @Dummy(auto = false) private AnnotationAttribute attributeConstraint;

    @Override
    public void setUpTestDoubles() {
        classNames = new HashSet<JavaClassName>() {
            {
                add(mock(JavaClassName.class));
            }
        };
        attributeConstraint = new AnnotationAttribute("group", ALL_GROUPS);
    }

    @Override
    public void setUpSubject() {
        finder = createSubjectWithConstructorArgs(ContextFinderImpl.class, new Object[]{CLASS_IN_SPEC_TREE}, packageRootFinder, classLocator,
                objectFactory);
    }

    public void testNothing() {
    }

    // TODO Groups: Figure out why this fails. jMock error.
    public void nsoTestGetContextNames() {
        expect.that(new Expectations() {
            {
                one(packageRootFinder).getPackageRoot(CLASS_IN_SPEC_TREE);
                will(returnValue(PACKAGE_ROOT));
                one(objectFactory).create(with(same(File.class)), with(same(PACKAGE_ROOT)));
                will(returnValue(packageRoot));
                expectClassFileFilterCreated();
                expectMethodFileFilterCreated();
                one(classLocator).locate(packageRoot, classFileFilter, methodFileFilter);
                will(returnValue(classNames));
            }

            private void expectClassFileFilterCreated() {
                one(objectFactory).create(with(same(ContextNamingConvention.class)));
                will(returnValue(contextNamingConvention));
                one(objectFactory).create(with(same(MarkingSchemeImpl.class)), with(same(Context.class)), with(same(contextNamingConvention)),
                        with(equal(attributeConstraint)));
                will(returnValue(contextMarkingScheme));
                one(objectFactory)
                        .create(with(same(ClassWithContextAnnotationFileFilter.class)), with(same(packageRoot)), with(same(contextMarkingScheme)));
                will(returnValue(classFileFilter));
            }

            private void expectMethodFileFilterCreated() {
                one(objectFactory).create(with(same(SpecificationNamingConvention.class)));
                will(returnValue(specificationNamingConvention));
                one(objectFactory).create(with(equal(MarkingSchemeImpl.class)), with(equal(Specification.class)),
                        with(equal(specificationNamingConvention)), with(equal(attributeConstraint)));
                will(returnValue(specificationMarkingScheme));
                one(objectFactory)
                        .create(with(same(ClassWithMarkedMethodsFileFilter.class)), with(same(packageRoot)), with(same(specificationMarkingScheme)));
                will(returnValue(methodFileFilter));
            }
        });
        final JavaClassName[] names = finder.getContextNames(ALL_GROUPS);
        expect.that(names).isEqualTo(classNames.toArray(new JavaClassName[classNames.size()]));
    }
}
