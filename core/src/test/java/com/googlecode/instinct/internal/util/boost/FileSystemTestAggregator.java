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

package com.googlecode.instinct.internal.util.boost;

import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.locate.ClassLocator;
import com.googlecode.instinct.internal.locate.ClassLocatorImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import java.io.File;
import java.io.FileFilter;
import java.util.Set;
import java.util.regex.Pattern;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

final class FileSystemTestAggregator implements TestAggregator {
    private final ClassLocator locator = new ClassLocatorImpl();
    private final ClassEdge classEdge = new ClassEdgeImpl();
    private final File root;

    FileSystemTestAggregator(final File root) {
        this.root = root;
    }

    public Test aggregate(final String suiteName, final String regex) {
        final JavaClassName[] matches = findMatches(regex, root);
        return buildSuite(suiteName, matches);
    }

    private JavaClassName[] findMatches(final String regex, final File root) {
        final Set<JavaClassName> classNames = locator.locate(root, new TestRegexFilter(regex));
        return classNames.toArray(new JavaClassName[classNames.size()]);
    }

    private Test buildSuite(final String suiteName, final JavaClassName[] classes) {
        final TestSuite result = new TestSuite(suiteName);
        for (final JavaClassName cls : classes) {
            addClass(cls, result);
        }
        return result;
    }

    @SuppressWarnings({"unchecked"})
    private void addClass(final JavaClassName clsName, final TestSuite result) {
        final String qualified = clsName.getFullyQualifiedName();
        final Class<? extends TestCase> cls = (Class<? extends TestCase>) classEdge.forName(qualified);
        result.addTestSuite(cls);
    }

    private static final class TestRegexFilter implements FileFilter {
        private final Pattern pattern;

        private TestRegexFilter(final String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        public boolean accept(final File pathname) {
            if (pathname.isDirectory()) {
                return false;
            } else {
                final String absolutePath = pathname.getAbsolutePath();
                final String s = absolutePath.substring(0, absolutePath.lastIndexOf(".class"));
                return pattern.matcher(s).matches();
            }
        }
    }
}
