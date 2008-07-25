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

package com.googlecode.instinct.actor;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import java.lang.reflect.Field;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ADummyAutoWireDeterminatorWithANamedFieldNotToAutoWire {
    @Subject(implementation = DummyAutoWireDeterminator.class) private AutoWireDeterminator autoWireDeterminator;

    @BeforeSpecification
    public void before() {
        autoWireDeterminator = new DummyAutoWireDeterminator();
    }

    @Specification
    public void respondsWithTrue() {
        expect.that(autoWireDeterminator.f(field("dummyCharSequence"))).isFalse();
    }

    private Field field(final String fieldName) {
        return getFieldByName(WithNamedFieldsNotToAutoWire.class, fieldName);
    }

    @SuppressWarnings({"ALL"})
    private static final class WithNamedFieldsNotToAutoWire {
        @Dummy(auto = false) CharSequence dummyCharSequence;
    }
}
