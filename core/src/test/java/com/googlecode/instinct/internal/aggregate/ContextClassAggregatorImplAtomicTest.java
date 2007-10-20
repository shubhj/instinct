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
import java.util.HashSet;
import java.util.Set;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.locate.ClassLocator;
import com.googlecode.instinct.internal.locate.ClassWithContextAnnotationFileFilter;
import com.googlecode.instinct.internal.locate.ClassWithMarkedMethodsFileFilter;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubjectWithConstructorArgs;
import org.jmock.Expectations;

@SuppressWarnings({"serial", "ClassExtendsConcreteCollection", "AnonymousInnerClassWithTooManyMethods"})
public final class ContextClassAggregatorImplAtomicTest extends InstinctTestCase {
    private static final Class<?> CLASS_IN_SPEC_TREE = ContextClassAggregatorImplAtomicTest.class;
    private static final String PACKAGE_ROOT = "";
    @Subject(auto = false) private ContextAggregator aggregator;
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

    @Override
    public void setUpTestDoubles() {
        classNames = new HashSet<JavaClassName>() {
            {
                add(mock(JavaClassName.class));
            }
        };
    }

    @Override
    public void setUpSubject() {
        aggregator = createSubjectWithConstructorArgs(ContextClassAggregatorImpl.class, new Object[]{CLASS_IN_SPEC_TREE}, packageRootFinder,
                classLocator, objectFactory);
    }

    public void testNothing() {
    }

    public void pvTestGetContextNames() {
        expect.that(new Expectations() {
            {
                one(packageRootFinder).getPackageRoot(CLASS_IN_SPEC_TREE);
                will(returnValue(PACKAGE_ROOT));
                one(objectFactory).create(File.class, PACKAGE_ROOT);
                will(returnValue(packageRoot));
                expectClassFileFilterCreated();
                expectMethodFileFilterCreated();
                one(classLocator).locate(packageRoot, classFileFilter, methodFileFilter);
                will(returnValue(classNames));
            }

            private void expectClassFileFilterCreated() {
                one(objectFactory).create(ContextNamingConvention.class);
                will(returnValue(contextNamingConvention));
                one(objectFactory).create(MarkingSchemeImpl.class, Context.class, contextNamingConvention);
                will(returnValue(contextMarkingScheme));
                one(objectFactory).create(ClassWithContextAnnotationFileFilter.class, packageRoot, contextMarkingScheme);
                will(returnValue(classFileFilter));
            }

            private void expectMethodFileFilterCreated() {
                one(objectFactory).create(SpecificationNamingConvention.class);
                will(returnValue(specificationNamingConvention));
                one(objectFactory).create(MarkingSchemeImpl.class, Specification.class, specificationNamingConvention);
                will(returnValue(specificationMarkingScheme));
                one(objectFactory).create(ClassWithMarkedMethodsFileFilter.class, packageRoot, specificationMarkingScheme);
                will(returnValue(methodFileFilter));
            }
        });
        final JavaClassName[] names = aggregator.getContextNames();
        expect.that(names).equalTo(classNames.toArray(new JavaClassName[classNames.size()]));
    }
}
