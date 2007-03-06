package com.googlecode.instinct.behaviourexpect;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.NameMatchBuilder;

@Suggest("Rename or remove.")
public interface MethodInvocations extends NameMatchBuilder {
    <T> T one(final T mockedObject);
}
