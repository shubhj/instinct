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

package com.googlecode.instinct.internal.util.instance;

import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassWithoutParamChecks;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

@SuppressWarnings({"ExceptionClassNameDoesntEndWithException", "UnusedDeclaration"})
public final class ObjectFactoryImplAtomicTest extends InstinctTestCase {
    private static final Class<ObjectFactory> AN_INTERFACE_1 = ObjectFactory.class;
    private static final Class<Serializable> AN_INTERFACE_2 = Serializable.class;
    @Subject private ObjectFactory factory;
    @Mock private Reader reader;
    @Mock private Writer writer;
    @Stub private String string;

    // Note. Because this is used in code that needs to accept nulls, we can't null check argument values.
    public void testConformsToClassTraits() {
        checkClassWithoutParamChecks(ObjectFactoryImpl.class, ObjectFactory.class);
    }

    public void testRefusesToCreateInterfaces() {
        checkRejectsInterfaces(AN_INTERFACE_1);
        checkRejectsInterfaces(AN_INTERFACE_2);
    }

    @Suggest("Check for two constructors with params of the same type, one subtype of the other.")
    public void testCreatesInstancesOfConcreteClasses() {
        checkSimpleArgumentsSucceed();
        checkMultipleConstructorSucceeds();
        checkSubclassTypeInferenceSucceed();
        checkIncorrectOrderingFails();
        checkBadConstructorParametersFails();
    }

    @SuppressWarnings({"UnnecessaryBoxing"})
    public void testPerformsUnboxingOfAutoboxedParametersWhenMatchingConstructor() {
        checkSucceeds(ClassWithConstructors.class, Boolean.valueOf(false));
        checkSucceeds(ClassWithConstructors.class, true);
        checkSucceeds(ClassWithConstructors.class, (byte) 1);
        checkSucceeds(ClassWithConstructors.class, (char) 1);
        checkSucceeds(ClassWithConstructors.class, (short) 1);
        checkSucceeds(ClassWithConstructors.class, 1);
        checkSucceeds(ClassWithConstructors.class, 1L);
        checkSucceeds(ClassWithConstructors.class, 1F);
        checkSucceeds(ClassWithConstructors.class, 1D);
    }

    public void testRejectsConcreteClassesWithRestrictiveAcessModifiers() {
        checkInstantiationConstructorsWithoutAccessFails();
    }

    private void checkSimpleArgumentsSucceed() {
        checkSucceeds(ObjectFactoryImpl.class);
    }

    private void checkMultipleConstructorSucceeds() {
        checkSucceeds(ClassWithConstructors.class, reader);
        checkSucceeds(ClassWithConstructors.class, string);
        checkSucceeds(ClassWithConstructors.class, reader, writer);
    }

    private void checkSubclassTypeInferenceSucceed() {
        checkSucceeds(ClassWithConstructors.class, reader, writer);
        checkSucceeds(ClassWithConstructors.class, mock(InputStreamReader.class, "reader1"), mock(PrintWriter.class, "writer1"));
        checkSucceeds(ClassWithConstructors.class, mock(InputStreamReader.class, "reader2"), mock(PrintWriter.class, "writer2"));
        checkSucceeds(ClassWithConstructors.class, mock(FilterReader.class), mock(FilterWriter.class));
    }

    private void checkIncorrectOrderingFails() {
        checkFails(ClassWithConstructors.class, writer, reader);
    }

    private void checkBadConstructorParametersFails() {
        checkFails(ObjectCreationException.class);
        checkFails(ClassWithConstructors.class, writer, reader);
    }

    private void checkInstantiationConstructorsWithoutAccessFails() {
        checkFails(ClassWithConstructors.class);
    }

    private <T> void checkSucceeds(final Class<T> toCreate, final Object... values) {
        factory.create(toCreate, values);
    }

    private <T> void checkFails(final Class<T> toCreate, final Object... values) {
        checkFailsWithException(ObjectCreationException.class, toCreate, values);
    }

    private <T, E extends RuntimeException> void checkFailsWithException(final Class<E> expectedException, final Class<T> toCreate,
            final Object... values) {
        expectException(expectedException, new Runnable() {
            public void run() {
                factory.create(toCreate, values);
            }
        });
    }

    private <T> void checkRejectsInterfaces(final Class<T> anInterface) {
        expectException(IllegalArgumentException.class, new Runnable() {
            public void run() {
                new ObjectFactoryImpl().create(anInterface);
            }
        });
    }

    @SuppressWarnings({"UtilityClassWithPublicConstructor", "ClassWithTooManyConstructors"})
    public static class ClassWithConstructors {
        public ClassWithConstructors(final String s) {
        }

        public ClassWithConstructors(final Reader r) {
        }

        public ClassWithConstructors(final FileReader r) {
        }

        public ClassWithConstructors(final Reader r, final Writer w) {
        }

        public ClassWithConstructors(final boolean b) {
        }

        public ClassWithConstructors(final byte b) {
        }

        public ClassWithConstructors(final char c) {
        }

        public ClassWithConstructors(final short s) {
        }

        public ClassWithConstructors(final int i) {
        }

        public ClassWithConstructors(final long l) {
        }

        public ClassWithConstructors(final double d) {
        }

        public ClassWithConstructors(final float f) {
        }

        ClassWithConstructors(final Integer i) {
        }

        private ClassWithConstructors() {
        }
    }
}
