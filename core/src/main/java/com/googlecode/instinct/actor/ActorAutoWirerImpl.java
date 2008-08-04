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

package com.googlecode.instinct.actor;

import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdgeImpl;
import com.googlecode.instinct.internal.locate.field.MarkedFieldLocator;
import com.googlecode.instinct.internal.locate.field.MarkedFieldLocatorImpl;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import com.googlecode.instinct.marker.naming.MockNamingConvention;
import com.googlecode.instinct.marker.naming.StubNamingConvention;
import fj.Effect;
import fj.F;
import fj.data.List;
import java.lang.reflect.Field;

@SuppressWarnings({"OverlyCoupledClass"})
public final class ActorAutoWirerImpl implements ActorAutoWirer {
    private final MarkedFieldLocator markedFieldLocator = new MarkedFieldLocatorImpl();
    private final FieldEdge fieldEdge = new FieldEdgeImpl();
    private final SpecificationDoubleCreator dummyCreator = new DummyCreator();
    private final SpecificationDoubleCreator stubCreator = new StubCreator();
    private final SpecificationDoubleCreator mockCreator = new MockCreator();

    @Suggest("Add in subject auto-wiring. Do it last, as it may require doubles.")
    @Fix("This needs to make sure we don't wire fields twice, say marked with @Mock but named stubFoo. See StubCreatorAtomicTest.")
    public List<Field> autoWireFields(final Object instanceToAutoWire) {
        checkNotNull(instanceToAutoWire);
        final List<Field> dummies = autoWireDummies(instanceToAutoWire);
        final List<Field> stubs = autoWireStubs(instanceToAutoWire);
        final List<Field> mocks = autoWireMocks(instanceToAutoWire);
        return List.<Field>nil().append(dummies).append(stubs).append(mocks).reverse();
    }

    private List<Field> autoWireDummies(final Object instanceToAutoWire) {
        final MarkingScheme dummyMarkingScheme = new MarkingSchemeImpl(Dummy.class, new DummyNamingConvention(), IGNORE);
        return autoWireMarkedFields(instanceToAutoWire, dummyCreator, new DummyAutoWireDeterminator(), dummyMarkingScheme);
    }

    private List<Field> autoWireStubs(final Object instanceToAutoWire) {
        final MarkingScheme stubMarkingScheme = new MarkingSchemeImpl(Stub.class, new StubNamingConvention(), IGNORE);
        return autoWireMarkedFields(instanceToAutoWire, stubCreator, new StubAutoWireDeterminator(), stubMarkingScheme);
    }

    private List<Field> autoWireMocks(final Object instanceToAutoWire) {
        final MarkingScheme mockMarkingScheme = new MarkingSchemeImpl(Mock.class, new MockNamingConvention(), IGNORE);
        return autoWireMarkedFields(instanceToAutoWire, mockCreator, new MockAutoWireDeterminator(), mockMarkingScheme);
    }

    private List<Field> autoWireMarkedFields(final Object instanceToAutoWire, final SpecificationDoubleCreator doubleCreator,
            final F<Field, Boolean> autoWireDeterminator, final MarkingScheme markingScheme) {
        final List<Field> fieldsToAutowire = markedFieldLocator.locateAll(instanceToAutoWire.getClass(), markingScheme).filter(autoWireDeterminator);
        fieldsToAutowire.foreach(new Effect<Field>() {
            public void e(final Field field) {
                autoWireField(instanceToAutoWire, field, doubleCreator);
            }
        });
        return fieldsToAutowire;
    }

    @SuppressWarnings({"CatchGenericClass", "OverlyBroadCatchBlock"})
    private void autoWireField(final Object instanceToAutoWire, final Field field, final SpecificationDoubleCreator doubleCreator) {
        try {
            final Object createdDouble = doubleCreator.createDouble(field.getType(), field.getName());
            field.setAccessible(true);
            fieldEdge.set(field, instanceToAutoWire, createdDouble);
        } catch (Throwable throwable) {
            final String message =
                    "Unable to autowire a specification double value into field '" + field.getName() + "' (type " + field.getType().getSimpleName() +
                            ") in class " + instanceToAutoWire.getClass().getSimpleName();
            throw new AutoWireException(message, throwable);
        }
    }
}
