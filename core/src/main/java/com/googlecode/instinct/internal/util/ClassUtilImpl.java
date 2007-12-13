package com.googlecode.instinct.internal.util;

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

public final class ClassUtilImpl implements ClassUtil {

    public boolean isJavaLibraryClass(final Class<?> cls) {
        final String packageName = cls.getPackage().getName();
        return packageName.startsWith("java.") || packageName.startsWith("javax.");
    }

    public boolean isBooleanType(final Class<?> classy) {
        return Boolean.class.isAssignableFrom(classy) || boolean.class.isAssignableFrom(classy);
    }

    public String capitalizeFirstCharacter(final String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
