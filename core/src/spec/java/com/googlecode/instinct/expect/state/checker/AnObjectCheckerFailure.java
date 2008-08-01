/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.expect.state.checker;

import com.googlecode.instinct.data.Person;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public final class AnObjectCheckerFailure {
    private static final String GREETING = "greeting";
    private static final String BLAH = "blah";

    @Specification
    public void shouldReturnAReadableErrorForNotNull() {
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(null);
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                objectChecker.isNotNull();
            }
        }, "not null", "null");
    }

    @Specification
    public void shouldReturnAReadableErrorForEquals() {
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(GREETING);
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                objectChecker.isEqualTo(BLAH);
            }
        }, quoted("blah"), quoted("greeting"));
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                objectChecker.isNotEqualTo(GREETING);
            }
        }, "not " + quoted("greeting"), quoted("greeting"));
    }

    @Specification
    public void shouldReturnAReadableErrorForTypeChecking() {
        final ObjectChecker<Integer> integerObjectChecker = new ObjectCheckerImpl<Integer>(1);
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                integerObjectChecker.isNotTheSameInstanceAs(1);
            }
        }, "not same(<1>)", "<1>");
        final Person p1 = new Person("Daffy", 3.4);
        final ObjectCheckerImpl<Person> personObjectChecker = new ObjectCheckerImpl<Person>(p1);
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                personObjectChecker.isNotTheSameInstanceAs(p1);
            }
        }, "not same(<Person[ name=Daffy, height=3.4 ]>)", "<Person[ name=Daffy, height=3.4 ]>");
    }

    @Specification
    public void shouldReturnAReadableErrorForProperties() {
        final Person peepz = new Person("Peepz");
        final ObjectCheckerImpl<Person> personObjectChecker = new ObjectCheckerImpl<Person>(peepz);
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                personObjectChecker.hasBeanProperty("surname", String.class);
            }
        }, "Person.getSurname() and/or Person.setSurname(String) should exist.",
                "Person.getSurname() and/or Person.setSurname(String) does not exist.");
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                personObjectChecker.hasBeanProperty("married", boolean.class);
            }
        }, "Person.isMarried() and/or Person.setMarried(boolean) should exist.",
                "Person.isMarried() and/or Person.setMarried(boolean) does not exist.");
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                personObjectChecker.hasBeanProperty("age", int.class);
            }
        }, "Person.getAge() and/or Person.setAge(int) should exist.", "Person.getAge() and/or Person.setAge(int) does not exist.");//Sanity Check.
        expect.that(peepz).hasBeanProperty("name", String.class);
    }

    @Specification
    public void shouldReturnAReadableErrorForPropertiesWithAValue() {
        final Person peepz = new Person("Peepz", 6.2);
        final ObjectChecker<Person> personObjectChecker = new ObjectCheckerImpl<Person>(peepz);
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                personObjectChecker.hasBeanPropertyWithValue("name", String.class, equalTo("Pedro"));
            }
        }, "Person.getName() should exist and it should return a value of \"Pedro\".",
                "Person.getName() does not exist and/or does not return a value of \"Pedro\".");
        expectErrorMessage(new ErrorCommand() {
            public void execute() {
                personObjectChecker.hasBeanPropertyWithValue("height", double.class, equalTo(6.1));
            }
        }, "Person.getHeight() should exist and it should return a value of <6.1>.",
                "Person.getHeight() does not exist and/or does not return a value of <6.1>.");
    }

    private String quoted(final String value) {
        return "\"" + value + "\"";
    }

    private void expectErrorMessage(final ErrorCommand command, final String expected, final String got) {
        try {
            command.execute();
            throw new RuntimeException("The expectation did not fail");
        } catch (AssertionError e) {
            final String expectedMessage = "\nExpected: " + expected + "\n     got: " + got + "\n";
            expect.that(e.getMessage()).isEqualTo(expectedMessage);
        }
    }

    private interface ErrorCommand {
        void execute();
    }
}