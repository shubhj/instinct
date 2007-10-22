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

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.FieldEdgeImpl;
import com.googlecode.instinct.internal.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.locate.MarkedFieldLocatorImpl;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;

public final class ActorAutoWirerImpl implements ActorAutoWirer {
    private final MarkedFieldLocator markedFieldLocator = new MarkedFieldLocatorImpl();
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final SpecificationDoubleCreator dummyCreator = new DummyCreator();
    private final FieldEdge fieldEdge = new FieldEdgeImpl();

    public void autoWireFields(final Object instanceToAutoWire) {
        checkNotNull(instanceToAutoWire);
        autoWireDummies(instanceToAutoWire);
    }

    private void autoWireDummies(final Object instanceToAutoWire) {
        autoWireDummies(dummyCreator, instanceToAutoWire, createDummyMarkingScheme());
    }

    private void autoWireDummies(final SpecificationDoubleCreator doubleCreator, final Object instanceToAutoWire, final MarkingScheme markingScheme) {
        final Field[] fields = markedFieldLocator.locateAll(instanceToAutoWire.getClass(), markingScheme);
        for (final Field field : fields) {
            if (autoWireDummy(field)) {
                final Object createdDouble = doubleCreator.createDouble(field.getType(), field.getName());
                field.setAccessible(true);
                fieldEdge.set(field, instanceToAutoWire, createdDouble);
            }
        }
    }

    private MarkingScheme createDummyMarkingScheme() {
        final NamingConvention namingConvention = objectFactory.create(DummyNamingConvention.class);
        return objectFactory.create(MarkingSchemeImpl.class, Dummy.class, namingConvention);
    }

    private boolean autoWireDummy(final AnnotatedElement dummyField) {
        final Dummy annotation = dummyField.getAnnotation(Dummy.class);
        return annotation != null && annotation.auto();
    }
}
