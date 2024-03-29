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
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.io.File;

public final class JavaClassNameFactoryImplAtomicTest extends InstinctTestCase {
    @Subject private JavaClassNameFactory factory;
    @Mock private File classesRoot;
    @Mock private File fileDir;

    public void testConformsToClassTraits() {
        checkClass(JavaClassNameFactoryImpl.class, JavaClassNameFactory.class);
    }

    public void testCreate() {
        final JavaClassName name = factory.create(classesRoot, fileDir);
        expect.that(name).isNotNull();
        expect.that(JavaClassNameImpl.class.equals(name.getClass())).isTrue();
    }
}
