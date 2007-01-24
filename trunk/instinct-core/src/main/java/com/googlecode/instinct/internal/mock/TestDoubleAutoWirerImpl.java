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

package com.googlecode.instinct.internal.mock;

import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.core.naming.DummyNamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedFieldLocatorImpl;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class TestDoubleAutoWirerImpl implements TestDoubleAutoWirer {
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private MarkedFieldLocator fieldLocator = new MarkedFieldLocatorImpl();

    @Suggest("Come back here after creating an marked field locator & use it here")
    public void wire(final Object instance) {
        checkNotNull(instance);
        fieldLocator.locateAll(instance.getClass(), Dummy.class, objectFactory.create(DummyNamingConvention.class));
        // check fields - non-final & null value
        // set accessible
        // Creator double & set field
    }
}
