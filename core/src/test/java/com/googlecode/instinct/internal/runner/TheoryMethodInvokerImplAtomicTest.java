package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.internal.util.MethodInvoker;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public class TheoryMethodInvokerImplAtomicTest extends InstinctTestCase {
    
    public void testConformsToClassTraits() {
        checkClass(TheoryMethodInvokerImpl.class, MethodInvoker.class);
    }


}
