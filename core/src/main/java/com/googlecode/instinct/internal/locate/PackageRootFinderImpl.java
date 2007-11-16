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

import java.net.URL;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

@SuppressWarnings({"HardcodedFileSeparator"})
public final class PackageRootFinderImpl implements PackageRootFinder {
    @Suggest({"Move Class to a boundary", "Move resource loading into a ResourceLoader class"})
    public <T> String getPackageRoot(final Class<T> classToFindRootOf) {
        checkNotNull(classToFindRootOf);
        final String fqn = classToFindRootOf.getName();
        final String classResourceNoLeadingSlash = fqn.replace('.', '/') + ".class";
        final String classResourcePath = '/' + classResourceNoLeadingSlash;
        final URL classResourceUrl = classToFindRootOf.getResource(classResourcePath);
        final String absolutePath = classResourceUrl.getFile();
        return absolutePath.replace(classResourceNoLeadingSlash, "");
    }
}
