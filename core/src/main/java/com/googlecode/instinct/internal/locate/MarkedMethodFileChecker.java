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

import com.googlecode.instinct.internal.edge.EdgeException;
import com.googlecode.instinct.internal.util.ClassInstantiator;
import com.googlecode.instinct.internal.util.ClassInstantiatorImpl;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;

@Fix("Remove dupe with MarkedFileChecker.")
public final class MarkedMethodFileChecker implements MarkedFileChecker {
    private final MarkedMethodLocator markedMethodLocator = new MarkedMethodLocatorImpl();
    private final ClassInstantiator classInstantiator = new ClassInstantiatorImpl();
    private final File packageRoot;

    public MarkedMethodFileChecker(final File packageRoot) {
        checkNotNull(packageRoot);
        this.packageRoot = packageRoot;
    }

    public boolean isMarked(final File classFile, final MarkingScheme markingScheme) {
        checkNotNull(classFile, markingScheme);
        return classFile.getName().endsWith(".class") && checkClass(classFile, markingScheme);
    }

    private boolean checkClass(final File classFile, final MarkingScheme markingScheme) {
        try {
            final Class<?> candidateClass = classInstantiator.instantiateClass(classFile, packageRoot);
            final Collection<Method> methods = markedMethodLocator.locateAll(candidateClass, markingScheme);
            return !methods.isEmpty();
        } catch (EdgeException e) {
            return false;
        }
    }
}
