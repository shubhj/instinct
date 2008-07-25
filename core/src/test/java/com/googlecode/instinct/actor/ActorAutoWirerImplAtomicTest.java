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

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.internal.util.Reflector.getFieldByName;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import fj.data.List;
import java.lang.reflect.Field;
import org.jmock.api.ExpectationError;

@Suggest("Also add in subject autowiring")
public final class ActorAutoWirerImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = ActorAutoWirerImpl.class) private ActorAutoWirer actorAutoWirer;
    @Stub private AClassWithMarkedFieldsToAutoWire instanceWithFieldsToWire;
    @Stub private AClassWithMarkedFieldsToNotAutowire instanceWithFieldsNotToWire;
    @Stub private AClassWithBadlyMarkedStubsToAutoWire instanceWithBadlyMarkedStubsToWire;
    @Stub private AClassWithBadlyMarkedMocksToAutoWire instanceWithBadlyMarkedMocksToWire;
    @Stub private AClassWithFieldsMarkedUsingANamingConvention instanceWithFieldsMarkedUsingANamingConvention;
    @Stub private AClassWithFieldsThatAreNonNull instanceWithFieldsThatAreNonNull;

    public void testConformsToClassTraits() {
        checkClass(ActorAutoWirerImpl.class, ActorAutoWirer.class);
    }

    public void testWillAutoWireDummyFieldEvenIfCurrentValueIsNotNull() {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsThatAreNonNull, "dummy");
        expectException(IllegalInvocationException.class, new Runnable() {
            public void run() {
                ((CharSequence) value).charAt(0);
            }
        });
    }

    public void testWillAutoWireStubFieldEvenIfNotNull() {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsThatAreNonNull, "stub");
        expect.that(value).isNotEqualTo("");
    }

    public void testWillAutoWireMockFieldEvenIfNotNull() {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsThatAreNonNull, "mock");
        expectException(ExpectationError.class, new Runnable() {
            public void run() {
                ((CharSequence) value).charAt(0);
            }
        });
    }

    // TODO: TestDouble
    public void testAutoWiresSubjectsByNamingConvention() {
    }

    // TODO: TestDouble
    public void testAutoWiresSubjectsByAnnotation() {
    }

    // TODO: TestDouble
    public void testWillWireMoreThanOneSubject() {
    }

    public void testAutoWiresDummiesByNamingConvention() {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsMarkedUsingANamingConvention, "dummySequence");
        expectException(IllegalInvocationException.class, new Runnable() {
            public void run() {
                ((CharSequence) value).charAt(0);
            }
        });
    }

    public void testAutoWiresDummiesByAnnotation() throws Exception {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsToWire, "dummy");
        expectException(IllegalInvocationException.class, new Runnable() {
            public void run() {
                ((CharSequence) value).charAt(0);
            }
        });
    }

    public void testDoesNotAutoWireDummiesWhenNotRequested() throws Exception {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsNotToWire, "dummy");
        expect.that(value).isNull();
    }

    public void testAutoWiresStubsByNamingConvention() {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsMarkedUsingANamingConvention, "stubString");
        expect.that(value).isNotNull();
    }

    public void testAutoWiresStubsByAnnotation() throws Exception {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsToWire, "stub");
        expect.that(value).isNotNull();
    }

    public void testDoesNotAutoWireStubsWhenNotRequested() throws Exception {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsNotToWire, "stub");
        expect.that(value).isNull();
    }

    public void testAutoWiresMocksByNamingConvention() {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsMarkedUsingANamingConvention, "mockSequence");
        expectException(ExpectationError.class, new Runnable() {
            public void run() {
                ((CharSequence) value).charAt(0);
            }
        });
    }

    public void testAutoWiresMocksByAnnotation() throws Exception {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsToWire, "mock");
        expectException(ExpectationError.class, new Runnable() {
            public void run() {
                ((CharSequence) value).charAt(0);
            }
        });
    }

    public void testDoesNotAutoWireMocksWhenNotRequested() throws Exception {
        final Object value = autoWireAndGetFieldValue(instanceWithFieldsNotToWire, "mock");
        expect.that(value).isNull();
    }

    public void testDisplaysADecentExceptionWhenTryingToStubIncorrectlyMarkedField() {
        expectException(AutoWireException.class, new Runnable() {
            public void run() {
                actorAutoWirer.autoWireFields(instanceWithBadlyMarkedStubsToWire);
            }
        });
    }

    public void testDisplaysADecentExceptionWhenTryingToMockIncorrectlyMarkedField() {
        expectException(AutoWireException.class, new Runnable() {
            public void run() {
                actorAutoWirer.autoWireFields(instanceWithBadlyMarkedMocksToWire);
            }
        });
    }

    @Specification
    public void testReturnsFieldsThatWereAutoWired() {
        final List<Field> fieldList = actorAutoWirer.autoWireFields(instanceWithFieldsToWire);
        expect.that(fieldList).isOfSize(3);
        expect.that(fieldList).containsItem(getField(instanceWithFieldsToWire, "dummy"));
        expect.that(fieldList).containsItem(getField(instanceWithFieldsToWire, "stub"));
        expect.that(fieldList).containsItem(getField(instanceWithFieldsToWire, "mock"));
    }

    // TODO: TestDouble
    public void testWiresArraysOfDummies() {
    }

    // TODO: TestDouble
    public void testWiresArraysOfStubs() {
    }

    // TODO: TestDouble
    public void testWiresArraysOfMocks() {
    }

    private Object autoWireAndGetFieldValue(final Object instance, final String fieldName) {
        actorAutoWirer.autoWireFields(instance);
        try {
            final Field field = getField(instance, fieldName);
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getField(final Object instance, final String fieldName) {
        final Field field = getFieldByName(instance.getClass(), fieldName);
        field.setAccessible(true);
        return field;
    }

    @SuppressWarnings({"ALL"})
    private static final class AClassWithBadlyMarkedStubsToAutoWire {
        @Stub private CharSequence stub;
    }

    @SuppressWarnings({"ALL"})
    private static final class AClassWithBadlyMarkedMocksToAutoWire {
        @Mock private String mock;
    }

    @SuppressWarnings({"ALL"})
    private static final class AClassWithMarkedFieldsToAutoWire {
        @Subject private String subject;
        @Mock private CharSequence mock;
        @Stub private String stub;
        @Dummy private CharSequence dummy;
    }

    @SuppressWarnings({"ALL"})
    private static final class AClassWithMarkedFieldsToNotAutowire {
        @Subject(auto = false) private String subject;
        @Mock(auto = false) private CharSequence mock;
        @Stub(auto = false) private String stub;
        @Dummy(auto = false) private CharSequence dummy;
    }

    @SuppressWarnings({"ALL"})
    private static final class AClassWithFieldsMarkedUsingANamingConvention {
        private String subject;
        private CharSequence mockSequence;
        private String stubString;
        private CharSequence dummySequence;
    }

    @SuppressWarnings({"ALL"})
    private static final class AClassWithFieldsThatAreNonNull {
        @Subject private String subject = "";
        @Mock private CharSequence mock = "";
        @Stub private String stub = "";
        @Dummy private CharSequence dummy = "";
    }
}
