package com.googlecode.instinct.behaviourexpect;

import java.util.ArrayList;
import java.util.List;
import org.jmock.builder.IdentityBuilder;
import org.jmock.builder.StubBuilder;
import org.jmock.core.Stub;
import org.jmock.core.stub.ReturnStub;

public final class BehaviourExpect {
    private static final BehaviourExpect expect = new BehaviourExpect();
    private final List<String> strings = new ArrayList<String>();
    private final SomeClass someClass = new SomeClass(strings);

    public void testOfBehaviourExpectationApi() {

        // Option 1
        expect.that(one(strings).add("abc")).will(returnValue(true));
        expect.that().one(strings).clear();

        // Option 2 - jMock 2 style
        expect.that(new Expectations() {{
            one(strings).add("abc"); will(returnValue('E'));
            one(strings).clear();
        }});

        someClass.doStuff();
    }

    public <T> T one(final T mockedObject) {
        return mockedObject;
    }

    public BehaviourExpect that() {
        return this;
    }

    public void that(final Expectations expectation) {
    }

    public <T> StubBuilder that(final T mockedObject) {
        return (StubBuilder) mockedObject;
    }

    public IdentityBuilder will(final Stub stubAction) {
        return null;
    }

    public Stub returnValue(final Object returnValue) {
        return new ReturnStub(returnValue);
    }

    private static class SomeClass {
        private final List<String> strings;

        private SomeClass(final List<String> strings) {
            this.strings = strings;
        }

        void doStuff() {
        }
    }

    // jMock2 class
    private static class Expectations {
    }

/*

   // Option 1 - JMock 1/EasyMock hybrid
   Magazine magazine = mock(Magazine.class);
   Stack stack = mock(Stack.class);
   MagazineRack rack = new MagazineRack();
   expect.one(stack.push(magazine));
   expect.call(stack.push(magazine));
   rack.addToPile(magazine);

   Magazine magazine = mock(Magazine.class);
   Stack stack = mock(Stack.class);
   MagazineRack rack = new MagazineRack();
   expect.one(stack.pop()).will(returnValue(magazine));
   Magazine taken = rack.takeFromPile();
   expect.that(taken).isSame(magazine);

   Magazine magazine = mock(Magazine.class);
   Stack stack = mock(Stack.class);
   MagazineRack rack = new MagazineRack();
   expect.one(stack.push(any(Magazine.class)));    // can also add constraints here
   rack.addToPile(magazine);


   // Ideas:
   // - Mockery.mock() returns an imposter that allows expectations to be set when methods are called,
   // - call/ignore, never, one, times/exactly, atLeast(times)
   //   ==> for looseness, default to any number of times (i.e. ignore)
   //
   // TODO:
   // - ordered calls?
   // - What about supporting jMock 1 syntax, like expect.that(once()).method(matches("^replace.*"));


   // jMock2
   exactly(int count) {
   one(T mockObject)
   atLeast(int count)
   between(int minCount, int maxCount)
   atMost(int count)

   // Instinct Mockery
   once()
   times(int expectedNumberOfCalls)  ==> exactly??
   times(int minNumberOfCalls, int maxNumberOfCalls)
   atLeastOnce()  ==> atLeast(times)
   anyTimes()  ==> ignore
   never()  ==> for negative testing, enforcing something, adding increased readability to tests.

*/

}
