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
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class DirectoryFilterAtomicTest extends InstinctTestCase {
    private File pathname;
    private FileFilter filter;

    public void testConformsToClassTraits() {
        checkClass(DirectoryFilter.class, FileFilter.class);
    }

    public void testAccept() {
        checkAccept(true);
        checkAccept(false);
    }

    private void checkAccept(final boolean value) {
        expects(pathname).method("isDirectory").will(returnValue(value));
        assertEquals(value, filter.accept(pathname));
    }

    @Override
    public void setUpTestDoubles() {
        pathname = mock(File.class);
    }

    @Override
    public void setUpSubject() {
        filter = new DirectoryFilter();
    }
}
