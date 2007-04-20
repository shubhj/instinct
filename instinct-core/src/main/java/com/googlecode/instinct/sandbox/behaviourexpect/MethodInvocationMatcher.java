package com.googlecode.instinct.sandbox.behaviourexpect;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.builder.ArgumentsMatchBuilder;
import org.jmock.builder.IdentityBuilder;
import org.jmock.core.Constraint;
import org.jmock.core.Stub;

@Suggest({"Include other cardinality methods, from jMock2.Expectations here. Also see mockery.",
        "Include jMock 2 cardinality methods??"})
public interface MethodInvocationMatcher {
    //extends NameMatchBuilder {

    <T> T one(final T mockedObject);

    @Suggest("Change return type to jMock2 class?")
    ArgumentsMatchBuilder method(String name);

    @Suggest("Change return type to jMock2 class?")
    ArgumentsMatchBuilder method(Constraint nameConstraint);

    @Suggest("Change return type to jMock2 class?")
    IdentityBuilder will(Stub stubAction);
}
