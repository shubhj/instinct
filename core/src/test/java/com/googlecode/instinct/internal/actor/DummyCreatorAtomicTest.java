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

package com.googlecode.instinct.internal.actor;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.proxy.ProxyGenerator;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import net.sf.cglib.proxy.MethodInterceptor;
import org.jmock.Expectations;

// Note. Cannot use auto-wired dummies here as we're testing the dummy creator.
public final class DummyCreatorAtomicTest extends InstinctTestCase {
    @Dummy(auto = false) private Class<Object> type = Object.class;
    @Dummy(auto = false) private Object proxy = new Object();
    @Subject(auto = false) private SpecificationDoubleCreator dummyCreator;
    @Mock private ProxyGenerator proxyGenerator;
    @Mock private ObjectFactory objectFactory;
    @Mock private MethodInterceptor methodInterceptor;

    @Override
    public void setUpSubject() {
        dummyCreator = createSubject(DummyCreator.class, proxyGenerator, objectFactory);
    }

    public void testConformsToClassTraits() {
        checkClass(DummyCreator.class, SpecificationDoubleCreator.class);
    }

    public void testCreatesProxiesForInterfacesUsingTheDummyMethodInterceptor() {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(DummyMethodInterceptor.class);
                will(returnValue(methodInterceptor));
                one(proxyGenerator).newProxy(type, methodInterceptor);
                will(returnValue(proxy));
            }
        });
        expect.that(dummyCreator.createDouble(type, "doesNotMatter")).sameInstanceAs(proxy);
    }
}
