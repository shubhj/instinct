package com.googlecode.instinct.internal.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.core.ASampleClass;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.MethodEdgeImpl;
import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import com.googlecode.instinct.test.reflect.TestSubjectCreator;
import org.jmock.Expectations;

import java.lang.reflect.Method;

public class TheoryMethodInvokerImplAtomicTest extends InstinctTestCase {
    @Mock private MethodEdge methodEdge;
    @Mock private ObjectFactory objectFactory;
    private MethodInvoker theoryMethodInvoker;
    private Method forAllMethod;
    @SuppressWarnings({"InstanceVariableOfConcreteClass"})
    private ASampleClass sampleClass;
    private Object[] params;

    @Override
    public void setUpSubject() {
        theoryMethodInvoker = TestSubjectCreator.createSubject(TheoryMethodInvokerImpl.class, objectFactory);
        sampleClass = new ASampleClass();
        forAllMethod = ASampleClass.class.getMethods()[0];
        params = forAllMethod.getParameterTypes();

    }

    public void testConformsToClassTraits() {
        checkClass(TheoryMethodInvokerImpl.class, MethodInvoker.class);
    }

    public void testWillThrowAnNotAValidTheoryMethodExceptionWhenInvokeMethodWithNoParamsIsCalled() {
        try {
            theoryMethodInvoker.invokeMethod(sampleClass, forAllMethod);
            fail();
        } catch (NotAValidTheoryMethodException e) {
        }
    }

    public void testWillInvokeMethodWithValue5() {
        expect.that(new Expectations() {
            {
                one(objectFactory).create(MethodEdgeImpl.class, forAllMethod); will(returnValue(methodEdge));
                one(methodEdge).invoke(sampleClass, forAllMethod, 5);
            }
        });
        theoryMethodInvoker.invokeMethod(sampleClass, forAllMethod, params);
    }
}
