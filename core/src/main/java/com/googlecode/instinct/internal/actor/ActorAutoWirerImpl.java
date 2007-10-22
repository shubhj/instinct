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

package com.googlecode.instinct.internal.actor;

import java.lang.reflect.Field;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdgeImpl;
import com.googlecode.instinct.internal.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.locate.MarkedFieldLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import com.googlecode.instinct.marker.naming.MockNamingConvention;
import com.googlecode.instinct.marker.naming.StubNamingConvention;

@SuppressWarnings({"OverlyCoupledClass"})
public final class ActorAutoWirerImpl implements ActorAutoWirer {
    private final MarkedFieldLocator markedFieldLocator = new MarkedFieldLocatorImpl();
    private final FieldEdge fieldEdge = new FieldEdgeImpl();
    private final SpecificationDoubleCreator dummyCreator = new DummyCreator();
    private final SpecificationDoubleCreator stubCreator = new StubCreator();
    private final SpecificationDoubleCreator mockCreator = new MockCreator();

    public void autoWireFields(final Object instanceToAutoWire) {
        checkNotNull(instanceToAutoWire);
        autoWireDummies(instanceToAutoWire);
        autoWireStubs(instanceToAutoWire);
        autoWireMocks(instanceToAutoWire);
    }

    private void autoWireDummies(final Object instanceToAutoWire) {
        final MarkingScheme dummyMarkingScheme = new MarkingSchemeImpl(Dummy.class, new DummyNamingConvention());
        autoWireMarkedFields(dummyCreator, instanceToAutoWire, dummyMarkingScheme, new DummyAutoWireDeterminator());
    }

    private void autoWireStubs(final Object instanceToAutoWire) {
        final MarkingScheme stubMarkingScheme = new MarkingSchemeImpl(Stub.class, new StubNamingConvention());
        autoWireMarkedFields(stubCreator, instanceToAutoWire, stubMarkingScheme, new StubAutoWireDeterminator());
    }

    private void autoWireMocks(final Object instanceToAutoWire) {
        final MarkingScheme mockMarkingScheme = new MarkingSchemeImpl(Mock.class, new MockNamingConvention());
        autoWireMarkedFields(mockCreator, instanceToAutoWire, mockMarkingScheme, new MockAutoWireDeterminator());
    }

    private void autoWireMarkedFields(final SpecificationDoubleCreator doubleCreator, final Object instanceToAutoWire,
            final MarkingScheme markingScheme, final AutoWireDeterminator autoWireDeterminator) {
        final Field[] fields = markedFieldLocator.locateAll(instanceToAutoWire.getClass(), markingScheme);
        for (final Field field : fields) {
            if (autoWireDeterminator.autoWire(field)) {
                autoWireField(instanceToAutoWire, field, doubleCreator);
            }
        }
    }

    @SuppressWarnings({"CatchGenericClass"})
    // SUPPRESS IllegalCatch {
    private void autoWireField(final Object instanceToAutoWire, final Field field, final SpecificationDoubleCreator doubleCreator) {
        try {
            final Object createdDouble = doubleCreator.createDouble(field.getType(), field.getName());
            field.setAccessible(true);
            fieldEdge.set(field, instanceToAutoWire, createdDouble);
        } catch (Throwable throwable) {
            final String message = "Unable to autowire a specification double value into field '" + field.getName() + "' (type "
                    + field.getType().getSimpleName() + ") in class " + instanceToAutoWire.getClass().getSimpleName();
            throw new AutoWireException(message, throwable);
        }
    }
    // } SUPPRESS IllegalCatch

    // Note. This stuff below is bollocks, as we cannot specify a shared type between annotations!

    private interface AutoWireDeterminator {
        boolean autoWire(Field field);
    }

    private static class DummyAutoWireDeterminator implements AutoWireDeterminator {
        public boolean autoWire(final Field field) {
            final Dummy annotation = field.getAnnotation(Dummy.class);
            return annotation != null && annotation.auto();
        }
    }

    private static class StubAutoWireDeterminator implements AutoWireDeterminator {
        public boolean autoWire(final Field field) {
            final Stub annotation = field.getAnnotation(Stub.class);
            return annotation != null && annotation.auto();
        }
    }

    private static class MockAutoWireDeterminator implements AutoWireDeterminator {
        public boolean autoWire(final Field field) {
            final Mock annotation = field.getAnnotation(Mock.class);
            return annotation != null && annotation.auto();
        }
    }
}
