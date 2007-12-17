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

package com.googlecode.instinct.test.suite;

import com.googlecode.instinct.internal.locate.cls.PackageRootFinder;
import com.googlecode.instinct.internal.locate.cls.PackageRootFinderImpl;
import java.io.File;
import junit.framework.Test;

public final class TestAggregatorImpl implements TestAggregator {
    private final PackageRootFinder packageRootFinder = new PackageRootFinderImpl();
    private final Class<?> classInTestTree;

    public TestAggregatorImpl(final Class<?> classInTestTree) {
        this.classInTestTree = classInTestTree;
    }

    public Test aggregate(final String suiteName, final String regex) {
        final File root = new File(packageRootFinder.getPackageRoot(classInTestTree));
        final TestAggregator aggregator = new FileSystemTestAggregator(root);
        return aggregator.aggregate(suiteName, regex);
    }
}
