package com.googlecode.instinct.behaviourexpect;

public final class BehaviourExpectations {

/*

    // Option 1 - JMock 1/EasyMock hybrid
    Magazine magazine = mock(Magazine.class);       // returns an imposter that allows expectations to be set
    MagazineRack rack = mock(MagazineRack.class);       // returns an imposter that allows expectations to be set

    expectThat(rack.addToPile(magazine));



    // ignore, never, one, times/exactly, atLeast(time)
    // ==> for looseness, default to any number of times (ignore)

    // jMock2
    exactly(int count) {
    one(T mockObject)
    atLeast(int count)
    between(int minCount, int maxCount)
    atMost(int count)

    // Mockery
    once()
    times(int expectedNumberOfCalls)
    times(int minNumberOfCalls, int maxNumberOfCalls)
    atLeastOnce()
    anyTimes()  ==> ignore



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
