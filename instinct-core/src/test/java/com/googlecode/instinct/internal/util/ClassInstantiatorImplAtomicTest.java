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
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class ClassInstantiatorImplAtomicTest extends InstinctTestCase {
    @Suggest("This string becomes a dummy.")
    private static final String FULLY_QUALIFIED_CLASS_NAME = "FQN";
    private ClassInstantiator instantiator;
    private File packageRoot;
    private File classFile;
    private EdgeClass edgeClass;
    private JavaClassNameFactory classNameFactory;
    private JavaClassName className;

    public void testConformsToClassTraits() {
        checkClass(ClassInstantiatorImpl.class, ClassInstantiator.class);
    }

    public void testInstantiateClass() {
        expects(classNameFactory).method("create").with(same(packageRoot), same(classFile)).will(returnValue(className));
        expects(className).method("getFullyQualifiedName").will(returnValue(FULLY_QUALIFIED_CLASS_NAME));
        expects(edgeClass).method("forName").with(eq(FULLY_QUALIFIED_CLASS_NAME)).will(returnValue(Class.class));
        final Class<?> actualClass = instantiator.instantiateClass(classFile);
        assertSame(Class.class, actualClass);
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "packageRoot");
        classFile = mock(File.class);
        edgeClass = mock(EdgeClass.class);
        classNameFactory = mock(JavaClassNameFactory.class);
        className = mock(JavaClassName.class);
    }

    @Override
    public void setUpSubject() {
        instantiator = new ClassInstantiatorImpl(packageRoot);
        insertFieldValue(instantiator, "classNameFactory", classNameFactory);
        insertFieldValue(instantiator, "edgeClass", edgeClass);
    }
}
