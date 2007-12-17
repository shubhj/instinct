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

package com.googlecode.instinct.internal.util.proxy;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.internal.util.Reflector.getMethod;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.CallbackFilter;

// See http://forum.java.sun.com/thread.jspa?threadID=616151&messageID=3429247 for details on bridge methods.
public final class IgnoreBridgeMethodsCallbackFilterAtomicTest extends InstinctTestCase {
    @Subject(implementation = IgnoreBridgeMethodsCallbackFilter.class) private CallbackFilter callbackFilter;
    private Method toStringMethod;
    private Method nonBridgeMethod;
    private Method bridgeMethod;

    @Override
    public void setUpTestDoubles() {
        toStringMethod = getMethod(SubClassWithBridgeMethods.class, "toString");
        nonBridgeMethod = getMethod(SubClassWithBridgeMethods.class, "bridgeMethod", String.class);
        bridgeMethod = getMethod(SubClassWithBridgeMethods.class, "bridgeMethod", Object.class);
    }

    public void testConformsToClassTraits() {
        checkClass(IgnoreBridgeMethodsCallbackFilter.class, CallbackFilter.class);
    }

    public void testReturnsZeroForNonBridgeMethods() {
        expect.that(callbackFilter.accept(toStringMethod)).isEqualTo(0);
        expect.that(callbackFilter.accept(nonBridgeMethod)).isEqualTo(0);
    }

    public void testReturnsOneForBridgeMethods() {
        expect.that(callbackFilter.accept(bridgeMethod)).isEqualTo(1);
    }

    @SuppressWarnings({"DesignForExtension"})
    private static class SuperClassWithGenericMethods<T> {
        public void bridgeMethod(final T t) {
        }
    }

    private static final class SubClassWithBridgeMethods extends SuperClassWithGenericMethods<String> {
        // Note. This method has a different erasure than the superclass bridgeMethod, so the compiler will generate a bridge method.
        @Override
        public void bridgeMethod(final String t) {
        }
    }
}
