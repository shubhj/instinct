package com.googlecode.instinct.expect;

import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import static com.googlecode.instinct.expect.Mocker12.same;
import com.googlecode.instinct.expect.behaviour.BehaviourExpectations;
import com.googlecode.instinct.expect.state.ObjectChecker;
import com.googlecode.instinct.expect.state.StateExpectations;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassWithoutParamChecks;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;

@Suggest({"Breadcrumb:",
        "Behaviour expectations plan.",
        "1. Drive out that(Expectations) to support jMock 2",
        "2. Move Mocker12 & JMock12Mockery infrastructure over to jMock2. Fix tests as required.",
        "3. Use that() to drive out facade on top of ObjectChecker that delegates to either a state or",
        "mock aware checker depending on object status (i.e. is it a mock?)."})
public final class ExpectThatImplAtomicTest extends InstinctTestCase {
    private ExpectThat expectThat;
    private StateExpectations stateExpectations;
    private BehaviourExpectations behaviourExpectations;
    private Object object;
    private ObjectChecker<?> objectChecker;

    @Override
    public void setUpTestDoubles() {
        stateExpectations = mock(StateExpectations.class);
        behaviourExpectations = mock(BehaviourExpectations.class);
        object = mock(Object.class);
        objectChecker = mock(ObjectChecker.class);
    }

    @Override
    public void setUpSubject() {
        expectThat = createSubject(ExpectThatImpl.class, stateExpectations, behaviourExpectations);
    }

    public void testConformsToClassTraits() {
        checkClassWithoutParamChecks(ExpectThatImpl.class, ExpectThat.class);
    }

    @Suggest("Write a delgation checker to check this for all methods.")
    public void testObjectFormThatDelegatesToStateExpectationsObjectFormThat() {
        expects(stateExpectations).method("that").with(same(object)).will(returnValue(objectChecker));
        assertSame(objectChecker, expectThat.that(object));
    }
}