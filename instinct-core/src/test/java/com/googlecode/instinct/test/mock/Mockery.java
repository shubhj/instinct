package com.googlecode.instinct.test.mock;

import com.googlecode.instinct.internal.util.Suggest;
import org.jmock.Mock;
import org.jmock.builder.NameMatchBuilder;
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
public final class Mockery {
    private static final VerifierImpl VERIFIER = new VerifierImpl();
    private static final MockControllerHolder CONTROLLER_HOLDER = new MockControllerHolderImpl();

    private Mockery() {
        throw new UnsupportedOperationException();
    }

    @Suggest({"Will need to keep a track of the mock by adding it to a map so we can get to the control & the proxy"})
    public static <T> T mock(final Class<T> toMock) {
        final Mock controller = createMockController(toMock);
        final Object mockedObject = controller.proxy();
        VERIFIER.addVerifiable(controller);
        CONTROLLER_HOLDER.addControl(controller, mockedObject);
        return (T) mockedObject;
    }

    public static NameMatchBuilder expects(final Object mockedObject, final InvocationMatcher expectation) {
        final Mock mockController = CONTROLLER_HOLDER.getMockController(mockedObject);
        return mockController.expects(expectation);
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

    public static void verify() {
        VERIFIER.verify();
    }

    private static <T> Mock createMockController(final Class<T> toMock) {
        return toMock.isInterface() ? createInterfaceMock(toMock) : createConcreteMock(toMock);
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

public static IMocksControl getMockController(final Object mock) {
    throw new UnsupportedOperationException();
//        return MOCK_FACTORY.getMockController(mock);
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
