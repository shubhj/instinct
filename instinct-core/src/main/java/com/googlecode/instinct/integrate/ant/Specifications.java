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

package com.googlecode.instinct.integrate.ant;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import org.apache.tools.ant.Project;

public final class Specifications {
    private final ClassLocator classLocator = new ClassLocatorImpl();
    private final Project project;
    private File specPackageRoot;

    public Specifications(final Project project) {
        checkNotNull(project);
        this.project = project;
    }

    public void setDir(final String dir) {
        checkNotWhitespace(dir);
        specPackageRoot = new File(dir);
        if (!specPackageRoot.exists()) {
            specPackageRoot = new File(project.getBaseDir(), dir);
            if (!specPackageRoot.exists()) {
                throw new IllegalArgumentException("Specifications directory '" + dir + "' does not exist");
            }
        }
    }

    public JavaClassName[] getContextClasses() {
        checkPreconditions();
        final FileFilter filter = new AnnotationFileFilter(specPackageRoot, BehaviourContext.class);
        return classLocator.locate(specPackageRoot, filter);
    }

    private void checkPreconditions() {
        if (specPackageRoot == null) {
            throw new IllegalStateException("Specification package root must be specified");
        }
    }
}
