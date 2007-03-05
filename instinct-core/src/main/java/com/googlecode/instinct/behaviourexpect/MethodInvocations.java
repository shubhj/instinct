package com.googlecode.instinct.behaviourexpect;

import org.jmock.builder.NameMatchBuilder;

public interface MethodInvocations {
    <T> T one(final T mockedObject);
    <T> NameMatchBuilder call(final T mockedObject);
}
