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
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker.eq;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;

public final class ClassInstantiatorImplAtomicTest extends InstinctTestCase {
    @Suggest("This string becomes a dummy.")
    private static final String FULLY_QUALIFIED_CLASS_NAME = "FQN";
    private ClassInstantiator instantiator;
    private File packageRoot;
    private File classFile;
    private ClassEdge classEdge;
    private JavaClassNameFactory classNameFactory;
    private JavaClassName className;

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "packageRoot");
        classFile = mock(File.class);
        classEdge = mock(ClassEdge.class);
        classNameFactory = mock(JavaClassNameFactory.class);
        className = mock(JavaClassName.class);
    }

    @Override
    public void setUpSubject() {
        instantiator = createSubject(ClassInstantiatorImpl.class, classNameFactory, classEdge);
    }

    public void testConformsToClassTraits() {
        checkClass(ClassInstantiatorImpl.class, ClassInstantiator.class);
    }

    public void testInstantiatesClassFromClassFiles() {
        expects(classNameFactory).method("create").with(same(packageRoot), same(classFile)).will(returnValue(className));
        expects(className).method("getFullyQualifiedName").will(returnValue(FULLY_QUALIFIED_CLASS_NAME));
        expects(classEdge).method("forName").with(eq(FULLY_QUALIFIED_CLASS_NAME)).will(returnValue(Class.class));
        final Class<?> actualClass = instantiator.instantiateClass(classFile, packageRoot);
        assertSame(Class.class, actualClass);
    }

    public void testInstantiatesClassesFromClassNames() {
        expects(classEdge).method("forName").with(eq(String.class.getName())).will(returnValue(String.class));
        final Class<?> actualClass = instantiator.instantiateClass(String.class.getName());
        expect.that(actualClass == String.class).isTrue();
    }
}
