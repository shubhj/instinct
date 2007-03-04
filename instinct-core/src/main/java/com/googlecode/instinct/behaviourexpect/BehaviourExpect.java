package com.googlecode.instinct.behaviourexpect;

import com.googlecode.instinct.internal.mock.Mockery;
import com.googlecode.instinct.internal.mock.MockeryImpl;
import org.jmock.builder.IdentityBuilder;
import org.jmock.builder.StubBuilder;
import org.jmock.core.Stub;
import org.jmock.core.stub.ReturnStub;

public final class BehaviourExpect {
    public static final BehaviourExpect expect = new BehaviourExpect();
    private final Mockery mockery = new MockeryImpl();

    public void testOfBehaviourExpectationApi() {
        expect.that(one(mockery).anything());
        will(returnValue(null));
//        expect.that(one(mockery).verify()); will(returnValue(null));
    }

    public <T> T one(final T mockedObject) {
        return mockedObject;
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

   // Mockery
   once()
   times(int expectedNumberOfCalls)  ==> exactly??
   times(int minNumberOfCalls, int maxNumberOfCalls)
   atLeastOnce()  ==> atLeast(times)
   anyTimes()  ==> ignore
   never()  ==> for negative testing, enforcing something, adding increased readability to tests.



   // Option 2 - JMock 2


*/

/*
JMock2

    Bid increment = new Bid(2);
    Bid maximumBid = new Bid(20);
    Bid beatableBid = new Bid(10);
    Bid unbeatableBid = maximumBid.add(new Bid(1));

    Lot lot = mock(Lot.class);
    AuctionSniperListener listener = mock(AuctionSniperListener.class, "listener");

    AuctionSniper sniper = new AuctionSniper(lot, increment, maximumBid, listener);

    public void testTriesToBeatTheLatestHighestBid() throws Exception {
        final Bid expectedBid = beatableBid.add(increment);

        checking(new Expectations() {{
            one(lot).bid(expectedBid);
        }});

        sniper.bidAccepted(lot, beatableBid);
    }

    public void testWillNotBidPriceGreaterThanMaximum() throws Exception {
        checking(new Expectations() {
            {
                ignoring(listener);
                never(lot).bid(with(any(Bid.class)));
            }
        });
        sniper.bidAccepted(lot, unbeatableBid);
    }

    public void testWillLimitBidToMaximum() throws Throwable {
        checking(new Expectations() {
            {
                exactly(1).of(lot).bid(maximumBid);
            }
        });

        sniper.bidAccepted(lot, maximumBid.subtract(new Bid(1)));
    }

    public void testWillNotBidWhenToldAboutBidsOnOtherItems() throws Throwable {
        final Lot otherLot = mock(Lot.class, "otherLot");

        checking(new Expectations() {
            {
                never(otherLot).bid(new Bid(10));
            }
        });

        sniper.bidAccepted(otherLot, beatableBid);
    }

    public void testWillAnnounceItHasFinishedIfPriceGoesAboveMaximum() {
        checking(new Expectations() {
            {
                exactly(1).of(listener).sniperFinished(sniper);
            }
        });

        sniper.bidAccepted(lot, unbeatableBid);
    }

    public void testCatchesExceptionsAndReportsThemToErrorListener() throws Exception {
        final AuctionException exception = new AuctionException("test");

        checking(new Expectations() {
            {
                allowing(lot).bid(with(anyBid));
                will(throwException(exception));
                exactly(1).of(listener).sniperBidFailed(sniper, exception);
            }
        });

        sniper.bidAccepted(lot, beatableBid);
    }

    private final Matcher<Bid> anyBid = IsAnything.anything();
*/
}
