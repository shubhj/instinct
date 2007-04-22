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

package com.googlecode.instinct.internal.aggregate;

import java.io.File;
import java.io.FileFilter;
import static com.googlecode.instinct.internal.aggregate.BehaviourContextAggregatorSlowTest.EXPECTED_CONTEXTS;
import com.googlecode.instinct.internal.aggregate.locate.AnnotationFileFilter;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.aggregate.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import com.googlecode.instinct.marker.annotate.BehaviourContext;
import com.googlecode.instinct.test.InstinctTestCase;

public final class ClassLocatorSlowTest extends InstinctTestCase {
    private PackageRootFinder packageRootFinder;
    private ClassLocator locator;

    @Override
    public void setUpSubject() {
        packageRootFinder = new PackageRootFinderImpl();
        locator = new ClassLocatorImpl();
    }

    public void testFindsCorrectNumberOfContexts() {
        final FileFilter filter = new AnnotationFileFilter(getSpecPackageRoot(), BehaviourContext.class);
        final JavaClassName[] names = locator.locate(getSpecPackageRoot(), filter);
        assertEquals(EXPECTED_CONTEXTS, names.length);
    }

    private File getSpecPackageRoot() {
        return new File(packageRootFinder.getPackageRoot(ClassLocatorSlowTest.class));
    }
}
