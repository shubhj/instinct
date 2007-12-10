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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import java.io.File;
import org.jmock.Expectations;

public final class ClassInstantiatorImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ClassInstantiator instantiator;
    @Mock private File packageRoot;
    @Mock private File classFile;
    @Mock private ClassEdge classEdge;
    @Mock private JavaClassNameFactory classNameFactory;
    @Mock private JavaClassName className;
    @Stub private String fullyQualifiedClassName;
    @Stub private Class<?> cls;

    @Override
    public void setUpSubject() {
        instantiator = createSubject(ClassInstantiatorImpl.class, classNameFactory, classEdge);
    }

    public void testConformsToClassTraits() {
        checkClass(ClassInstantiatorImpl.class, ClassInstantiator.class);
    }

    public void testInstantiatesClassFromClassFiles() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(classNameFactory).create(packageRoot, classFile);
                will(returnValue(className));
                atLeast(1).of(className).getFullyQualifiedName();
                will(returnValue(fullyQualifiedClassName));
                atLeast(1).of(classEdge).forName(fullyQualifiedClassName);
                will(returnValue(cls));
            }
        });
        final Class<?> actualClass = instantiator.instantiateClass(classFile, packageRoot);
        expect.that(actualClass == cls).isTrue();
    }

    public void testInstantiatesClassesFromClassNames() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(classEdge).forName(cls.getName());
                will(returnValue(cls));
            }
        });
        final Class<?> actualClass = instantiator.instantiateClass(cls.getName());
        expect.that(actualClass == cls).isTrue();
    }
}
