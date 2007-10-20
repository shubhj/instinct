/*
 * Copyright 2006-2007 Workingmouse
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

import java.io.File;
import java.io.FileFilter;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;

public final class ClassWithMarkedMethodsFileFilter implements FileFilter {
    private final MarkingScheme markingScheme;
    private final MarkedFileChecker markedFileChecker;

    public ClassWithMarkedMethodsFileFilter(final File packageRoot, final MarkingScheme markingScheme) {
        checkNotNull(packageRoot, markingScheme);
        this.markingScheme = markingScheme;
        markedFileChecker = new MethodMarkedFileChecker(packageRoot);
    }

    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        return !pathname.isDirectory() && markedFileChecker.isMarked(pathname, markingScheme);
    }
}
