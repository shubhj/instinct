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

import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;

public final class NullMasterImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = NullMasterImpl.class) private NullMaster nullMaster;

    public void testConformsToClassTraits() {
        checkClass(NullMasterImpl.class, NullMaster.class);
    }

    public void testCanAcceptANullParameterName() {
        nullMaster.check(this, null);
    }

    public void testNoNulls() {
        nullMaster.check(this);
    }

    public void testNullParamThrowsException() {
        checkNullParamThrowsException();
        checkNullParamThrowsException();
    }

    public void testRejectsNullParametersWithExplicitParamNames() {
        checkNullParamThrowsException("someParamName");
        checkNullParamThrowsException("someOtherParamName");
    }

    public void testRejectsANullParameterList() {
        checkRejectsNulls(1, new Object[]{null});
        checkRejectsNulls(1, new Object[]{null});
        checkRejectsNulls(2, this, null);
        checkRejectsNulls(3, this, this, null);
    }

    public void testAcceptsParameterList() {
        nullMaster.check(this);
        nullMaster.check(this, this);
        nullMaster.check(this, this, this);
    }

    public void testRejectsNestedObjectArrays() {
        final Object[] parameters = {this, new Object[]{null}};
        checkRejectsNulls(2, parameters);
    }

    private void checkRejectsNulls(final int badParamNumber, final Object... parameters) {
        expectException(IllegalArgumentException.class, "Parameter " + badParamNumber + " should not be null", new Runnable() {
            public void run() {
                nullMaster.check(parameters);
            }
        });
    }

    private void checkNullParamThrowsException() {
        expectException(IllegalArgumentException.class, new Runnable() {
            public void run() {
                nullMaster.check((Object) null);
            }
        });
    }

    private void checkNullParamThrowsException(final String parameterName) {
        expectException(IllegalArgumentException.class, parameterName + " parameter should not be null", new Runnable() {
            public void run() {
                nullMaster.check(null, parameterName);
            }
        });
    }
}
