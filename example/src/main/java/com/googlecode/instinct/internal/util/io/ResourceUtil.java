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

package com.googlecode.instinct.internal.util.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public final class ResourceUtil {
    private static final Class<ResourceUtil> RESOURCE_CLASS = ResourceUtil.class;

    private ResourceUtil() {
        throw new UnsupportedOperationException();
    }

    public static File getResourceAsFile(final String resourceName) {
        try {
            final URL resource = getResourceAsUrl(resourceName);
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getResourceAsFilePath(final String resourceName) {
        try {
            return getResourceAsFile(resourceName).getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getResourceAsStream(final String resourceName) {
        final InputStream stream = RESOURCE_CLASS.getResourceAsStream(resourceName);
        if (stream == null) {
            return RESOURCE_CLASS.getClassLoader().getResourceAsStream(resourceName);
        } else {
            return stream;
        }
    }

    public static URL getResourceAsUrl(final String resourceName) {
        final URL resourceUrl = RESOURCE_CLASS.getResource(resourceName);
        if (resourceUrl == null) {
            return RESOURCE_CLASS.getClassLoader().getResource(resourceName);
        } else {
            return resourceUrl;
        }
    }
}
