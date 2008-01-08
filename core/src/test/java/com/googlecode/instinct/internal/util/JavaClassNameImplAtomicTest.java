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
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.io.File;

public final class JavaClassNameImplAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(JavaClassNameImpl.class, JavaClassName.class);
    }

    public void testGetFullyQualifiedClassName() {
        checkGetFullyQualifiedClassName("/home/me/projects/src/", "/home/me/projects/src/com/foo/Bar.class", "com.foo.Bar");
        checkGetFullyQualifiedClassName("/home/me/projects/src", "/home/me/projects/src/com/foo/Bar.class", "com.foo.Bar");
        checkGetFullyQualifiedClassName("/home/me/projects/src/", "/home/me/projects/src/com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("/home/me/projects/src", "/home/me/projects/src/com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("/", "/com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("", "com/foo/bar/Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("\\", "\\com\\foo\\bar\\Baz.class", "com.foo.bar.Baz");
        checkGetFullyQualifiedClassName("/", "/com/foo/bar/class/Baz.class", "com.foo.bar.class.Baz");
    }

    private void checkGetFullyQualifiedClassName(final String classesRootPath, final String classFilePath,
            final String expectedFullyQualifiedClassName) {
        final JavaClassName className = new JavaClassNameImpl(new File(classesRootPath), new File(classFilePath));
        expect.that(className.getFullyQualifiedName()).isEqualTo(expectedFullyQualifiedClassName);
    }
}
