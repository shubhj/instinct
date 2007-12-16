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

import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorFactory;
import com.googlecode.instinct.internal.util.ClassInstantiatorFactoryImpl;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.MarkingScheme;
import java.io.File;

public final class MarkedClassFileChecker implements MarkedFileChecker {
    private AnnotationChecker annotationChecker = new AnnotationCheckerImpl();
    private ClassInstantiatorFactory instantiatorFactory = new ClassInstantiatorFactoryImpl();
    private final File packageRoot;

    public MarkedClassFileChecker(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    @Suggest("Write test that ensures that we reject non-classes & return false for EdgeExceptions")
    public boolean isMarked(final File classFile, final MarkingScheme markingScheme) {
        checkNotNull(classFile, markingScheme);
        return classFile.getName().endsWith(".class") && checkClass(classFile, markingScheme);
    }

    @Fix("Extend to find context classes based on naming convention")
    private boolean checkClass(final File classFile, final MarkingScheme markingScheme) {
        try {
            final ClassInstantiator instantiator = instantiatorFactory.create();
            final Class<?> candidateClass = instantiator.instantiateClass(classFile, packageRoot);
            return annotationChecker.isAnnotated(candidateClass, markingScheme.getAnnotationType(), markingScheme.getAttributeConstraint());
        } catch (EdgeException e) {
            return false;
        }
    }
}
