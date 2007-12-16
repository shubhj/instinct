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

import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.lang.Primordial;
import java.io.File;

public final class JavaClassNameImpl extends Primordial implements JavaClassName {
    private final File classesRoot;
    private final File classFile;

    public JavaClassNameImpl(final File classesRoot, final File classFile) {
        checkNotNull(classesRoot, classFile);
        this.classesRoot = classesRoot;
        this.classFile = classFile;
    }

    public String getFullyQualifiedName() {
        final String relativeClassPath = classFile.getAbsolutePath().substring(classesRoot.getAbsolutePath().length());
        final String dotsForSlashes = relativeClassPath.replaceAll("[/\\\\]", ".");
        final String noLeadingDot = dotsForSlashes.startsWith(".") ? dotsForSlashes.substring(1) : dotsForSlashes;
        return noLeadingDot.replaceAll("\\.class$", "");
    }
}
