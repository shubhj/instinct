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

package com.googlecode.instinct.internal.util.array;

import com.googlecode.instinct.internal.util.NullMaster;
import com.googlecode.instinct.internal.util.NullMasterImpl;
import java.util.ArrayList;
import java.util.List;

public final class ArrayFlattenerImpl implements ArrayFlattener {
    private static final NullMaster NULL_MASTER = new NullMasterImpl();

    // Note. Cannot use ParamChecker.check(Object[]) as it uses this method, hence stack overflow, so doesn't check embedded nulls.
    public Object[] flatten(final Object[] unflattened) {
        NULL_MASTER.check(unflattened, "unflattened");
        final List<Object> flattened = doFlatten(unflattened);
        return flattened.toArray(new Object[flattened.size()]);
    }

    private List<Object> doFlatten(final Object[] unflattened) {
        final List<Object> flattened = new ArrayList<Object>();
        for (final Object item : unflattened) {
            if (item instanceof Object[]) {
                flattened.addAll(doFlatten((Object[]) item));
            } else {
                flattened.add(item);
            }
        }
        return flattened;
    }
}
