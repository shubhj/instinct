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

package com.googlecode.instinct.internal.locate;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.io.File;
import java.io.FileFilter;
import org.jmock.Expectations;

public final class DirectoryFilterAtomicTest extends InstinctTestCase {
    @Subject(implementation = DirectoryFilter.class) private FileFilter filter;
    @Mock private File pathname;

    public void testConformsToClassTraits() {
        checkClass(DirectoryFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(true);
        checkAccept(false);
    }

    private void checkAccept(final boolean isADirectory) {
        expect.that(new Expectations() {
            {
                one(pathname).isDirectory(); will(returnValue(isADirectory));
            }
        });
        expect.that(filter.accept(pathname)).isEqualTo(isADirectory);
    }
}
