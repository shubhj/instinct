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
import java.util.Arrays;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import org.apache.tools.ant.Project;

public final class AnnotatedSpecificationAggregatorImpl implements AnnotatedSpecificationAggregator {
    private final ClassLocator classLocator = new ClassLocatorImpl();
    private final Project project;
    private File specPackageRoot;

    public AnnotatedSpecificationAggregatorImpl(final Project project) {
        checkNotNull(project);
        this.project = project;
    }

    public void setSpecificationRoot(final String specificationRoot) {
        checkNotWhitespace(specificationRoot);
        specPackageRoot = new File(project.getBaseDir(), specificationRoot);
    }

    public JavaClassName[] getContextNames() {
        checkPreconditions();
        System.out.println("packageRoot.getAbsolutePath() = " + specPackageRoot.getAbsolutePath());
        final JavaClassName[] names = classLocator.locate(specPackageRoot, new AnnotationFileFilter(specPackageRoot, BehaviourContext.class));
        System.out.println("Arrays.asList(names) = " + Arrays.asList(names));
        return names;
    }

    private void checkPreconditions() {
        if (specPackageRoot == null) {
            throw new IllegalStateException("Specification root was not specified");
        }
    }
}
