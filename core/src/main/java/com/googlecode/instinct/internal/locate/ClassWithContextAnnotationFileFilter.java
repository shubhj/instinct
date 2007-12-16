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

import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import java.io.File;
import java.io.FileFilter;

public final class ClassWithContextAnnotationFileFilter implements FileFilter {
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final File packageRoot;
    private final MarkingScheme markingScheme;

    public ClassWithContextAnnotationFileFilter(final File packageRoot, final MarkingScheme markingScheme) {
        checkNotNull(packageRoot, markingScheme);
        this.packageRoot = packageRoot;
        this.markingScheme = markingScheme;
    }

    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        final MarkedFileChecker checker = objectFactory.create(MarkedClassFileChecker.class, packageRoot);
        return !pathname.isDirectory() && checker.isMarked(pathname, markingScheme);
    }
}
