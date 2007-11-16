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

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.internal.aggregate.ContextAggregatorSlowTest.EXPECTED_CONTEXTS;
import com.googlecode.instinct.internal.locate.ClassLocator;
import com.googlecode.instinct.internal.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.locate.ClassWithContextAnnotationFileFilter;
import com.googlecode.instinct.internal.locate.ClassWithMarkedMethodsFileFilter;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.naming.ContextNamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import java.io.File;
import java.io.FileFilter;
import java.util.Set;

public final class ClassLocatorSlowTest extends InstinctTestCase {
    private PackageRootFinder packageRootFinder;
    private ClassLocator locator;

    @Override
    public void setUpSubject() {
        packageRootFinder = new PackageRootFinderImpl();
        locator = new ClassLocatorImpl();
    }

    public void testFindsCorrectNumberOfContexts() {
        final FileFilter filter1 = new ClassWithContextAnnotationFileFilter(getSpecPackageRoot(),
                new MarkingSchemeImpl(Context.class, new ContextNamingConvention(), IGNORE));
        final FileFilter filter2 = new ClassWithMarkedMethodsFileFilter(getSpecPackageRoot(),
                new MarkingSchemeImpl(Specification.class, new SpecificationNamingConvention(), IGNORE));
        final Set<JavaClassName> names = locator.locate(getSpecPackageRoot(), filter1, filter2);
        expect.that(names).isOfSize(EXPECTED_CONTEXTS);
    }

    private File getSpecPackageRoot() {
        return new File(packageRootFinder.getPackageRoot(ClassLocatorSlowTest.class));
    }
}
