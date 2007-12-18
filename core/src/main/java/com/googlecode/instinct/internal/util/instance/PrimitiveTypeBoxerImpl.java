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

package com.googlecode.instinct.internal.util.instance;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class PrimitiveTypeBoxerImpl implements PrimitiveTypeBoxer {
    public <T> Class<T> boxPrimitiveType(final Class<T> type) {
        checkNotNull(type);
        return boxPrimitive(type);
    }

    // SUPPRESS CyclomaticComplexity {
    @SuppressWarnings({"unchecked", "IfStatementWithTooManyBranches"})
    @Suggest("Can this be done using a generic map? Seems like the boxing does the wrong thing when added to the Map.")
    private <T> Class<T> boxPrimitive(final Class<T> argumentType) {
        if (argumentType == Boolean.TYPE) {
            return (Class<T>) Boolean.class;
        } else if (argumentType == Byte.TYPE) {
            return (Class<T>) Byte.class;
        } else if (argumentType == Character.TYPE) {
            return (Class<T>) Character.class;
        } else if (argumentType == Short.TYPE) {
            return (Class<T>) Short.class;
        } else if (argumentType == Integer.TYPE) {
            return (Class<T>) Integer.class;
        } else if (argumentType == Long.TYPE) {
            return (Class<T>) Long.class;
        } else if (argumentType == Float.TYPE) {
            return (Class<T>) Float.class;
        } else if (argumentType == Double.TYPE) {
            return (Class<T>) Double.class;
        } else {
            return argumentType;
        }
    }

    // } SUPPRESS CyclomaticComplexity
}
