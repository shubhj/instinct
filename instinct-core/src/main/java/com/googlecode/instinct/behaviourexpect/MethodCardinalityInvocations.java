package com.googlecode.instinct.behaviourexpect;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.NameMatchBuilder;

@Suggest("Include jMock 2 cardinality methods??")
public interface MethodCardinalityInvocations extends NameMatchBuilder {
    <T> T one(final T mockedObject);
}
