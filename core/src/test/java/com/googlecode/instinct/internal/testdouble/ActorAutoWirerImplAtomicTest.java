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

package com.googlecode.instinct.internal.testdouble;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import org.jmock.Expectations;

public final class ActorAutoWirerImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ActorAutoWirer actorAutoWirer;
    @Mock private MarkedFieldLocator markedFieldLocator;
    @Mock private ObjectFactory objectFactory;
    @Stub private SomeClassWithMarkedFieldsToAutoWire instanceWithFieldsToWire;
    @Stub private SomeClassWithMarkedFieldsToNotAutowire instanceWithFieldsNotToWire;
    @Dummy private MarkingScheme markingScheme;
    @Dummy private NamingConvention dummyMarkingConvention;

    @Override
    public void setUpSubject() {
        actorAutoWirer = createSubject(ActorAutoWirerImpl.class, markedFieldLocator, objectFactory);
    }

    public void testAutoWiresDummiesIntoClasses() {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(DummyNamingConvention.class);
                will(returnValue(dummyMarkingConvention));
                one(objectFactory).create(MarkingSchemeImpl.class, Dummy.class, dummyMarkingConvention);
                will(returnValue(markingScheme));
                one(markedFieldLocator).locateAll(instanceWithFieldsToWire.getClass(), markingScheme);
            }
        });
        actorAutoWirer.autoWireFields(instanceWithFieldsToWire);
    }

    private static final class SomeClassWithMarkedFieldsToAutoWire {
        @Subject private CharSequence aSubject;
        @Mock private CharSequence aMock;
        @Stub private CharSequence aStub;
        @Dummy private CharSequence aDummy;
    }

    private static final class SomeClassWithMarkedFieldsToNotAutowire {
        @Subject(auto = false) private CharSequence aSubject;
        @Mock(auto = false) private CharSequence aMock;
        @Stub(auto = false) private CharSequence aStub;
        @Dummy(auto = false) private CharSequence aDummy;
    }
}
