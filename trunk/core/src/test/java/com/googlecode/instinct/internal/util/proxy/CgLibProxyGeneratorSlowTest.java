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
import com.googlecode.instinct.internal.testdouble.DummyMethodInterceptor;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import java.util.ArrayList;
import java.util.List;

public final class CgLibProxyGeneratorSlowTest extends InstinctTestCase {
    @Subject(implementation = CgLibProxyGenerator.class) private ProxyGenerator proxyGenerator;

    public void testGeneratesProxiesForInterfaces() {
        final Iterable<?> proxy = proxyGenerator.newProxy(Iterable.class, new DummyMethodInterceptor());
        expect.that(proxy).isNotNull();
    }

    public void testGeneratesProxiesForNonFinalClasses() {
        final List<?> list = proxyGenerator.newProxy(ArrayList.class, new DummyMethodInterceptor());
        expect.that(list).isNotNull();
    }

    public void testProxiesStaticInnerClasses() {
        proxyGenerator.newProxy(StaticInnerClass.class, new DummyMethodInterceptor());
    }

    public void testProxiesNonStaticInnerClasses() {
        proxyGenerator.newProxy(NonStaticInnerClass.class, new DummyMethodInterceptor());
    }

    @SuppressWarnings({"ALL"})
    private class NonStaticInnerClass {
        public NonStaticInnerClass() {
        }
    }

    @SuppressWarnings({"ALL"})
    private static class StaticInnerClass {
        public StaticInnerClass() {
        }
    }
}