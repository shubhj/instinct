package com.googlecode.instinct.expect;

import com.googlecode.instinct.expect.behaviour.BehaviourExpectations;
import com.googlecode.instinct.expect.state.ObjectChecker;
import com.googlecode.instinct.expect.state.StateExpectations;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubject;

public final class ExpectThatImplAtomicTest extends InstinctTestCase {
    private ExpectThat expectThat;
    private StateExpectations stateExpectations;
    private BehaviourExpectations behaviourExpectations;
    private Object object;
    private ObjectChecker objectChecker;

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
        checkClass(ExpectThatImpl.class, ExpectThat.class);
    }

    public void testObjectFormThatDelegatesToStateExpectationsObjectFormThat() {
        expects(stateExpectations).method("that").with(same(object)).will(returnValue(objectChecker));
        assertSame(objectChecker, expectThat.that(object));
    }
}
