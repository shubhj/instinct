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

package com.googlecode.instinct.internal.lang;

import com.googlecode.instinct.internal.reflect.ReflectMaster;
import com.googlecode.instinct.internal.reflect.ReflectMasterImpl;

public final class FieldBasedEqualsMaster implements EqualsMaster {
    private final ReflectMaster master = new ReflectMasterImpl();

    public boolean equals(final Object o1, final Object o2) {
        if (bothNull(o1, o2)) {
            return true;
        } else if (eitherNull(o1, o2) || differentClasses(o1, o2)) {
            return false;
        } else {
            return determineEqualityFromFields(o1, o2);
        }
    }

    private boolean eitherNull(final Object o1, final Object o2) {
        return o1 == null || o2 == null;
    }

    private boolean bothNull(final Object o1, final Object o2) {
        return o1 == null && o2 == null;
    }

    private boolean determineEqualityFromFields(final Object o1, final Object o2) {
        final FieldValueSpec[] spec1 = master.getInstanceFields(o1);
        final FieldValueSpec[] spec2 = master.getInstanceFields(o2);
        if (spec1.length == 0) {
            throw new IllegalArgumentException("Illegal type for comparison " + o1.getClass());
        }
        for (int i = 0; i < spec1.length; i++) {
            if (!spec1[i].equals(spec2[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean differentClasses(final Object o1, final Object o2) {
        return o1.getClass() != o2.getClass();
    }
}
