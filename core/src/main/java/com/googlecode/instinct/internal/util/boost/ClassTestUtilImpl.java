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

public final class ClassTestUtilImpl implements ClassTestUtil {
    public boolean isImplementationOf(final Interface targetInterface, final Class cls) {
        final Class type = targetInterface.getType();
        return isAssignable(type, cls);
    }

    public boolean isSubclassOf(final Class superClass, final Class subClass) {
        return isAssignable(superClass, subClass);
    }

    private boolean isAssignable(final Class superType, final Class subType) {
        return superType.isAssignableFrom(subType);
    }
}
