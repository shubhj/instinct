package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Mock;
import org.jmock.cglib.CGLIBCoreMock;
import static org.jmock.core.AbstractDynamicMock.mockNameFromClass;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.stub.ReturnStub;

@SuppressWarnings({"unchecked"})
@Suggest({"Move this into the production tree -> pull out an interface & make non-static"})
public final class Mocker {
    private Mocker() {
        throw new UnsupportedOperationException();
    }

    @Suggest({"Will need to keep a track of the mock by adding it to a map so we can get to the control & the proxy"})
    public static <T> T mock(final Class<T> toMock) {
        return (T) mockController(toMock).proxy();
    }

    public static <T> Mock mockController(final Class<T> toMock) {
        return toMock.isInterface() ? createInterfaceMock(toMock) : createConcreteMock(toMock);
    }

    @Suggest({"Use this in a similar way to EasyMock.expect()", "Define some interfaces that restrict what can be done or use JMock's."})
    public static Mock expect() {
        // mock(Object.class).method("toString").will(return("some string"))
        // expect(Object.class, "toString").will(return("some string"))
        throw new UnsupportedOperationException();
    }

    public static InvocationMatcher once() {
        return new InvokeOnceMatcher();
    }

    public static Constraint same(final Object argument) {
        return new IsSame(argument);
    }

    public static Constraint anything() {
        return new IsAnything();
    }

    public static Constraint eq(final Object argument) {
        return new IsEqual(argument);
    }

    public static Stub returnValue(final Object returnValue) {
        return new ReturnStub(returnValue);
    }

    private static <T> Mock createInterfaceMock(final Class<T> toMock) {
        return new Mock(toMock);
    }

    private static <T> Mock createConcreteMock(final Class<T> toMock) {
        return new Mock(new CGLIBCoreMock(toMock, mockNameFromClass(toMock)));
    }

/*
public static <T> T createNiceMock(final Class<T> toMock) {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createNiceMock(toMock);
}

public static IMocksControl getControl(final Object mock) {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.getControl(mock);
}

public static IMocksControl createControl() {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createControl();
}

public static IMocksControl createNiceControl() {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createNiceControl();
}

public static IMocksControl createStrictControl() {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.createStrictControl();
}

public static void replay() {
    throw new UnsupportedOperationException();
//        MOCK_FACTORY.replay();
}

public static void verify() {
    throw new UnsupportedOperationException();
//        MOCK_FACTORY.verify();
}

public static void reset() {
    throw new UnsupportedOperationException();
//        MOCK_FACTORY.reset();
}*/
}
