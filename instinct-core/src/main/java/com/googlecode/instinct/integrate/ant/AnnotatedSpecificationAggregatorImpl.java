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
import java.util.Arrays;
import com.googlecode.instinct.core.annotate.BehaviourContext;
import com.googlecode.instinct.internal.aggregate.BehaviourContextAggregator;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;

public final class AnnotatedSpecificationAggregatorImpl implements AnnotatedSpecificationAggregator {
    private String specificationRoot;

    public BehaviourContextAggregator getAggregator() {
        return null;
    }

    public void setSpecificationRoot(final String specificationRoot) {
        checkNotWhitespace(specificationRoot);
        System.out.println("specificationRoot = " + specificationRoot);
        this.specificationRoot = specificationRoot;
    }

    public JavaClassName[] getContextNames() {
        // validate the path
        // resolve the absolute path (if neccessary)?
        final File packageRoot = new File(specificationRoot);
        final FileFilter filter = new AnnotationFileFilter(packageRoot, BehaviourContext.class);
        final JavaClassName[] names = new ClassLocatorImpl().locate(packageRoot, filter);
        System.out.println("Arrays.asList(names) = " + Arrays.asList(names));
        return names;
    }
}
