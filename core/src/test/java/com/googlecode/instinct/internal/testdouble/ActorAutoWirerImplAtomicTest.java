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

import java.lang.reflect.Field;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdge;
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

@SuppressWarnings({"InstanceVariableOfConcreteClass"})
public final class ActorAutoWirerImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ActorAutoWirer actorAutoWirer;
    @Mock private MarkedFieldLocator markedFieldLocator;
    @Mock private ObjectFactory objectFactory;
    @Mock private SpecificationDoubleCreator dummyCreator;
    @Mock private FieldEdge fieldEdge;
    @Stub private SomeClassWithMarkedFieldsToAutoWire instanceWithFieldsToWire;
    @Stub private SomeClassWithMarkedFieldsToNotAutowire instanceWithFieldsNotToWire;
    @Stub private Field[] dummyFields;
    @Dummy private MarkingScheme markingScheme;
    @Dummy private NamingConvention dummyMarkingConvention;

    @Override
    public void setUpSubject() {
        actorAutoWirer = createSubject(ActorAutoWirerImpl.class, markedFieldLocator, objectFactory, dummyCreator, fieldEdge);
    }

    public void testAutoWiresDummiesIntoClasses() {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(DummyNamingConvention.class);
                will(returnValue(dummyMarkingConvention));
                one(objectFactory).create(MarkingSchemeImpl.class, Dummy.class, dummyMarkingConvention);
                will(returnValue(markingScheme));
                one(markedFieldLocator).locateAll(instanceWithFieldsToWire.getClass(), markingScheme);
                will(returnValue(dummyFields));
                for (final Field field : dummyFields) {
                    final Object doubleInstance = one(dummyCreator).createDouble(field.getType(), field.getName());
                    one(fieldEdge).set(field, instanceWithFieldsToWire, doubleInstance);
                }
            }
        });
        actorAutoWirer.autoWireFields(instanceWithFieldsToWire);
    }
}
