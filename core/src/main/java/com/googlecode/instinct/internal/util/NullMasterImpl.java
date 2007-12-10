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

package com.googlecode.instinct.internal.util;

import com.googlecode.instinct.internal.util.array.ArrayFlattener;
import com.googlecode.instinct.internal.util.array.ArrayFlattenerImpl;

public final class NullMasterImpl implements NullMaster {
    private static final ArrayFlattener FLATTENER = new ArrayFlattenerImpl();

    public void check(final Object parameter, final String parameterName) {
        if (parameter == null) {
            throw new IllegalArgumentException(parameterName + " parameter should not be null");
        }
    }

    public void check(final Object... parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Parameter 1 should not be null");
        }
        checkElements(parameters);
    }

    private void checkElements(final Object[] parameters) {
        final Object[] flattenedParameters = FLATTENER.flatten(parameters);
        for (int i = 0; i < flattenedParameters.length; i++) {
            if (flattenedParameters[i] == null) {
                throw new IllegalArgumentException("Parameter " + (i + 1) + " should not be null");
            }
        }
    }
}
