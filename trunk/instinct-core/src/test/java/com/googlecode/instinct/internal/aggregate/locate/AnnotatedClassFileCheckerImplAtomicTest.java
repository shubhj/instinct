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

package com.googlecode.instinct.internal.aggregate.locate;

import java.io.File;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorFactory;
import static com.googlecode.instinct.mock.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class AnnotatedClassFileCheckerImplAtomicTest extends InstinctTestCase {
    private AnnotatedClassFileChecker checker;
    private File packageRoot;
    private File classFile;
    private AnnotationChecker annotationChecker;
    private ClassInstantiator instantiator;
    private ClassInstantiatorFactory instantiatorFactory;

    public void testProperties() {
        checkClass(AnnotatedClassFileCheckerImpl.class, AnnotatedClassFileChecker.class);
    }

//    public void testIsAnnotated() {
//        expects(instantiatorFactory).method("create").with(same(packageRoot)).will(returnValue(instantiator));
//        expects(instantiator).method("instantiateClass").with(same(classFile)).will(returnValue(Class.class));
//        expects(annotationChecker).method("isAnnotated").with(eq(Class.class), eq(BehaviourContext.class)).will(returnValue(true));
//        assertTrue(checker.isAnnotated(classFile, BehaviourContext.class));
//    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "mockPackageRoot");
        classFile = mock(File.class, "mockClassFile");
        annotationChecker = mock(AnnotationChecker.class);
        instantiator = mock(ClassInstantiator.class);
        instantiatorFactory = mock(ClassInstantiatorFactory.class);
    }

    @Override
    public void setUpSubject() {
        checker = new AnnotatedClassFileCheckerImpl(packageRoot);
        insertFieldValue(checker, "annotationChecker", annotationChecker);
        insertFieldValue(checker, "instantiatorFactory", instantiatorFactory);
    }
}
