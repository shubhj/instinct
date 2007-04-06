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
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.BehaviourContext;
import static com.googlecode.instinct.mock.Mocker.eq;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class AnnotatedBehaviourContextAggregatorImplAtomicTest extends InstinctTestCase {
    private static final Class<?> CLASS_IN_SPEC_TREE = AnnotatedBehaviourContextAggregatorImplAtomicTest.class;
    private static final JavaClassName[] CLASS_NAMES = {};
    private static final String PACKAGE_ROOT = "";
    private BehaviourContextAggregator aggregator;
    private PackageRootFinder packageRootFinder;
    private ClassLocator classLocator;
    private ObjectFactory objectFactory;
    private File packageRoot;
    private FileFilter fileFilter;

    public void testGetContextNames() {
        expects(packageRootFinder).method("getPackageRoot").with(same(CLASS_IN_SPEC_TREE)).will(returnValue(PACKAGE_ROOT));
        expects(objectFactory).method("create").with(same(File.class), eq(new Object[]{PACKAGE_ROOT})).will(returnValue(packageRoot));
        expects(objectFactory).method("create").with(same(AnnotationFileFilter.class), eq(new Object[]{packageRoot, BehaviourContext.class})).will(
                returnValue(fileFilter));
        expects(classLocator).method("locate").with(same(packageRoot), same(fileFilter)).will(returnValue(CLASS_NAMES));
        final JavaClassName[] names = aggregator.getContextNames();
        assertSame(CLASS_NAMES, names);
    }

    @Override
    public void setUpTestDoubles() {
        classLocator = mock(ClassLocator.class);
        packageRootFinder = mock(PackageRootFinder.class);
        objectFactory = mock(ObjectFactory.class);
        fileFilter = mock(FileFilter.class);
        packageRoot = mock(File.class);
    }

    @Override
    public void setUpSubject() {
        aggregator = new AnnotatedBehaviourContextAggregatorImpl(CLASS_IN_SPEC_TREE);
        insertFieldValue(aggregator, "packageRootFinder", packageRootFinder);
        insertFieldValue(aggregator, "classLocator", classLocator);
        insertFieldValue(aggregator, "objectFactory", objectFactory);
    }
}
