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
import java.io.FileFilter;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import static com.googlecode.instinct.mock.Mocker.eq;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;

public final class AnnotationFileFilterAtomicTest extends InstinctTestCase {
    private FileFilter filter;
    private File packageRoot;
    private File pathname;
    private ObjectFactory objectFactory;
    private AnnotatedClassFileChecker checker;

    public void testProperties() {
        checkClass(AnnotationFileFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(false, true, true);
        checkAccept(true, false, false);
    }

    private void checkAccept(final boolean pathIsADirectory, final boolean classHasAnnotation, final boolean isAnnotated) {
        expects(objectFactory).method("create").with(same(AnnotatedClassFileCheckerImpl.class), eq(new Object[]{packageRoot})).will(
                returnValue(checker));
        expects(pathname).method("isDirectory").will(returnValue(pathIsADirectory));
        if (!pathIsADirectory) {
            expects(checker).method("isAnnotated").with(same(pathname), same(BehaviourContext.class)).will(returnValue(classHasAnnotation));
        }
        final boolean accept = filter.accept(pathname);
        assertEquals(isAnnotated, accept);
    }

    @Override
    public void setUpTestDoubles() {
        packageRoot = mock(File.class, "mockPackageRoot");
        pathname = mock(File.class, "mockPathname");
        objectFactory = mock(ObjectFactory.class);
        checker = mock(AnnotatedClassFileChecker.class);
    }

    @Override
    public void setUpSubject() {
        filter = new AnnotationFileFilter(packageRoot, BehaviourContext.class);
        insertFieldValue(filter, "objectFactory", objectFactory);
    }
}
