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

package com.googlecode.instinct.internal.mock.constraint;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import java.util.Arrays;
import org.jmock.core.Constraint;

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
public final class ArrayElementsSame implements Constraint {
    private Object[] expectedArray;

    public ArrayElementsSame(final Object[] expectedArray) {
        checkNotNull(expectedArray);
        this.expectedArray = expectedArray;
    }

    @Suggest("Might need to come up with a better exception to throw on mock failure, a jMock one?")
    public boolean eval(final Object o) {
        checkNotNull(o);
        if (!(o instanceof Object[])) {
            throw new RuntimeException("I can only compare arrays!");
        }
        return checkArrayElements((Object[]) o);
    }

    public StringBuffer describeTo(final StringBuffer buffer) {
        checkNotNull(buffer);
        return buffer.append("sameElements(").append(Arrays.toString(expectedArray)).append(')');
    }

    private boolean checkArrayElements(final Object[] actualArray) {
        if (actualArray.length != expectedArray.length) {
            return false;
        }
        for (int i = 0; i < actualArray.length; i++) {
            if (actualArray[i] != expectedArray[i]) {
                return false;
            }
        }
        return true;
    }
}
