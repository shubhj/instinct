/*
 * Copyright (c) 2007, Your Corporation. All Rights Reserved.
 */

package com.googlecode.instinct.api;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.expect.state.checker.ObjectCheckerImpl;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AnObjectCheckerFailure {
    private static final String GREETING = "greeting";
    private static final String BLAH = "blah";

    @Specification
    public void shouldReturnAReadableErrorForNotNull() {
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(null);
        expectErrorMessage(new ErrorCommand() {public void execute() {objectChecker.isNotNull();}} , "not null", "null");
    }

    @Specification
    public void shouldReturnAReadableErrorForEquals() {
        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(GREETING);
        expectErrorMessage(new ErrorCommand() {public void execute() {objectChecker.isEqualTo(BLAH);}}, quoted("blah"), quoted("greeting"));
        expectErrorMessage(new ErrorCommand() {public void execute() {objectChecker.isNotEqualTo(GREETING);}}, "not " + quoted("greeting"),
                quoted("greeting"));
    }

    @Specification
    public void shouldReturnAReadableErrorForTypeChecking() {
        final ObjectChecker<Integer> objectChecker = new ObjectCheckerImpl<Integer>(1);
        expectErrorMessage(new ErrorCommand() {public void execute() {objectChecker.isNotTheSameInstanceAs(1);}}, "not same(<1>)","<1>");
    }

//    @Specification
//    public void shouldReturnAReadableErrorForProperties() {
//        final ObjectChecker<String> objectChecker = new ObjectCheckerImpl<String>(GREETING);
//        expectErrorMessage(new ErrorCommand() {public void execute() {objectChecker.hasBeanProperty("bytes1");}}, "String has property bytes1",
//                "could not find property");
//    }

    private String quoted(final String value) {
        return "\"" + value + "\"";
    }

    private void expectErrorMessage(final ErrorCommand command, final String expected, final String got) {
        try {
            command.execute();
            throw new RuntimeException("The expectation did not fail");
        } catch (AssertionError e) {
            expect.that(e.getMessage()).isEqualTo("\nExpected: " + expected + "\n     got: " + got + "\n");
        }
    }

    private interface ErrorCommand {
        void execute();
    }
}