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

import com.googlecode.instinct.internal.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.locate.MarkedFieldLocatorImpl;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.MarkingSchemeImpl;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.naming.DummyNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;

public final class ActorAutoWirerImpl implements ActorAutoWirer {
    private final MarkedFieldLocator markedFieldLocator = new MarkedFieldLocatorImpl();
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();

    public void autoWireFields(final Object instanceToAutoWire) {
        final NamingConvention namingConvention = objectFactory.create(DummyNamingConvention.class);
        final MarkingScheme markingScheme = objectFactory.create(MarkingSchemeImpl.class, Dummy.class, namingConvention);
        markedFieldLocator.locateAll(instanceToAutoWire.getClass(), markingScheme);
    }
}
