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

import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;

public final class AnnotationFileFilter implements FileFilter {
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final File packageRoot;
    private final Class<? extends Annotation> annotationType;

    public <T extends Annotation> AnnotationFileFilter(final File packageRoot, final Class<T> annotationType) {
        checkNotNull(packageRoot, annotationType);
        this.packageRoot = packageRoot;
        this.annotationType = annotationType;
    }

    public boolean accept(final File pathname) {
        checkNotNull(pathname);
        final AnnotatedClassFileChecker checker = objectFactory.create(AnnotatedClassFileCheckerImpl.class, packageRoot);
        return !pathname.isDirectory() && checker.isAnnotated(pathname, annotationType);
    }
}
