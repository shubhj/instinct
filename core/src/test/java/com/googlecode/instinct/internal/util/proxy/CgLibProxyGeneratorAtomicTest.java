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
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;
import org.jmock.Expectations;
import org.objenesis.Objenesis;

public final class CgLibProxyGeneratorAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ProxyGenerator proxyGenerator;
    @Mock private ObjectFactory objectFactory;
    @Mock private Objenesis objenesis;
    @Mock private CgLibEnhancer enhancer;
    @Stub private Class<?> classToProxy;
    @Stub private Class<?> interfaceToProxy;
    @Stub private Class<?> enhancedProxyType;
    @Dummy private Object proxy;
    @Dummy private MethodInterceptor methodInterceptor;

    @Override
    public void setUpSubject() {
        proxyGenerator = createSubject(CgLibProxyGenerator.class, objectFactory, objenesis);
    }

    public void testConformsToClassTraits() {
        checkClass(CgLibProxyGenerator.class, ProxyGenerator.class);
    }

    public void testGeneratesProxiesUsingCgLib() {
        checkGeneratesProxiesUsingCgLib(classToProxy);
        checkGeneratesProxiesUsingCgLib(interfaceToProxy);
    }

    public void testDoesNotProxyFinalClasses() {
        checkDoesNotProxyFinalClasses(String.class);
        checkDoesNotProxyFinalClasses(Class.class);
    }

    private <T> void checkGeneratesProxiesUsingCgLib(final Class<T> typeToProxy) {
        expectEnhancerCreated(typeToProxy);
        expect.that(new Expectations() {
            {
                one(enhancer).createClass();
                will(returnValue(enhancedProxyType));
                one(objenesis).newInstance(enhancedProxyType);
                will(returnValue(proxy));
            }
        });
        final Object createdProxy = proxyGenerator.newProxy(typeToProxy, methodInterceptor);
        expect.that(createdProxy).isTheSameInstanceAs(proxy);
    }

    private <T> void expectEnhancerCreated(final Class<T> typeToProxy) {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(CgLibEnhancerImpl.class);
                will(returnValue(enhancer));
                if (typeToProxy.isInterface()) {
                    one(enhancer).setSuperclass(Object.class);
                    one(enhancer).setInterface(typeToProxy);
                } else {
                    one(enhancer).setSuperclass(typeToProxy);
                }
                one(enhancer).setCallbackTypes(methodInterceptor.getClass(), NoOp.class);
                one(enhancer).setCallbackFilter(with(any(IgnoreBridgeMethodsCallbackFilter.class)));
                allowing(enhancer).setNamingPolicy(with(any(SignedClassSafeNamingPolicy.class)));
            }
        });
    }

    private <T> void checkDoesNotProxyFinalClasses(final Class<T> finalClass) {
        final String message = "Cannot proxy final class " + finalClass.getName();
        expectException(IllegalArgumentException.class, message, new Runnable() {
            public void run() {
                proxyGenerator.newProxy(finalClass, methodInterceptor);
            }
        });
    }
}
