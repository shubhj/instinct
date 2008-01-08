/*
 * Copyright 2006-2008 Workingmouse
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

package com.googlecode.instinct.internal.reflect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.lang.MethodSpec;
import com.googlecode.instinct.internal.lang.MethodSpecImpl;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class ReflectMethodMasterImplAtomicTest extends InstinctTestCase {
    private static final String CHURCH_METHOD_NAME = "getSmeetOthEchuRchontIme";
    private static final String FRIDAY_METHOD_NAME = "fridayIsHere";
    private static final String CRAPOLA_METHOD_NAME = "justSomeOldMethod";
    private static final Class<?>[] NO_PARAMETERS = {};
    private static final Class<?>[] CHURCH_PARAMETER_TYPES = {String.class, Map.class};
    private static final Class<?>[] MORE_PARAMETER_TYPES = {String.class, Map.class, List.class};
    private static final Class<?>[] LESS_PARAMETER_TYPES = {String.class};
    private static final Class<?>[] DIFFERENT_PARAMETER_TYPE = {String.class, Set.class};
    private static final Class<?> INTERFACE_ONE = TestInterfaceOne.class;
    private static final Class<?> INTERFACE_TWO = TestInterfaceTwo.class;
    private static final MethodSpec METHOD_CHURCH = new MethodSpecImpl(CHURCH_METHOD_NAME, CHURCH_PARAMETER_TYPES);
    private static final MethodSpec METHOD_FRIDAY = new MethodSpecImpl(FRIDAY_METHOD_NAME, NO_PARAMETERS);
    private static final MethodSpec METHOD_CRAPOLA = new MethodSpecImpl(CRAPOLA_METHOD_NAME, NO_PARAMETERS);
    @Subject(implementation = ReflectMethodMasterImpl.class) private ReflectMethodMaster master;

    public void testGetMethodBasic() {
        checkGetMethod(INTERFACE_ONE, METHOD_CHURCH);
        checkGetMethod(INTERFACE_ONE, METHOD_FRIDAY);
        checkGetMethod(INTERFACE_TWO, METHOD_CRAPOLA);
    }

    public void testNoMatchingMethod() {
        checkNoMatchingMethod(LESS_PARAMETER_TYPES);
        checkNoMatchingMethod(MORE_PARAMETER_TYPES);
        checkNoMatchingMethod(DIFFERENT_PARAMETER_TYPE);
    }

    public void testGetMethodWithSubtypeParam() {
        final Class<?>[] params = {String.class, WeakHashMap.class};
        final Method result = master.getMethod(INTERFACE_ONE, new MethodSpecImpl(CHURCH_METHOD_NAME, params));
        final Method expected = getMethod(INTERFACE_ONE, CHURCH_METHOD_NAME, CHURCH_PARAMETER_TYPES);
        expect.that(result).isEqualTo(expected);
    }

    public void testNullsIllegal() {
        checkNullsIllegal(null, METHOD_CHURCH);
        checkNullsIllegal(String.class, null);
    }

    private <T> void checkGetMethod(final Class<T> cls, final MethodSpec method) {
        expect.that(master.getMethod(cls, method)).isEqualTo(getMethod(cls, method.getName(), method.getParams()));
    }

    private <T> void checkNullsIllegal(final Class<T> cls, final MethodSpec method) {
        expectException(IllegalArgumentException.class, new Runnable() {
            public void run() {
                master.getMethod(cls, method);
            }
        });
    }

    private void checkNoMatchingMethod(final Class<?>[] parameterTypes) {
        expectException(NoSuchMethodError.class, new Runnable() {
            public void run() {
                final MethodSpec methodSpec = new MethodSpecImpl(CHURCH_METHOD_NAME, parameterTypes);
                master.getMethod(INTERFACE_ONE, methodSpec);
            }
        });
    }

    private <T> Method getMethod(final Class<T> cls, final String name, final Class<?>[] params) {
        return new ClassEdgeImpl().getMethod(cls, name, params);
    }

    @SuppressWarnings({"ALL"})
    private interface TestInterfaceOne {
        void fridayIsHere();

        Integer getSmeetOthEchuRchontIme(String frankyfurter, Map beanTypes);
    }

    @SuppressWarnings({"ALL"})
    private interface TestInterfaceTwo {
        void justSomeOldMethod();
    }
}
