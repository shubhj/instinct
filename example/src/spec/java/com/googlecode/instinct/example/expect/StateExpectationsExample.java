package com.googlecode.instinct.example.expect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.expect.state.StateExpectations;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;

/**
 * The examples below illustrate Instinct's state-based expectation API.
 * @see StateExpectations
 */
@SuppressWarnings({"MagicNumber", "unchecked"})
@RunWith(InstinctRunner.class)
@Context
public final class StateExpectationsExample {

    @Specification
    public void providesMatchersForMakingAssertionsAboutObjects() {
        expect.that("fred").isEqualTo("fred");
        expect.that("fred").isNotEqualTo("barney");
        expect.that(this).isTheSameInstanceAs(this);
        expect.that("fred").isNotTheSameInstanceAs("barney");
        expect.that(this).isOfType(StateExpectationsExample.class);
        expect.that(this).isNotNull();
        // Mixing with standard Hamcrest matchers
        expect.that("fred").matches(Matchers.startsWith("fr"), Matchers.containsString("ed"));
        expect.that("fred", Matchers.equalTo("fred"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutClasses() {
        expect.that(String.class).typeCompatibleWith(Comparable.class);
        expect.that(Comparable.class).notTypeCompatibleWith(String.class);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutStrings() {
        expect.that("andersdabeerz").isEqualToIgnoringCase("AndersDaBeerz");
        expect.that("andersdabeerz").startsWith("anders");
        expect.that("andersdabeerz").containsString("da");
        expect.that("andersdabeerz").endsWith("beerz");
        expect.that("andersdabeerz").isEqualToIgnoringWhiteSpace(" andersdabeerz ");
        expect.that("andersdabeerz").doesNotContainString("water");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutComparables() {
        expect.that(1).isEqualTo(1);
        expect.that(1).isGreaterThan(0);
        expect.that(1).isGreaterThanOrEqualTo(0);
        expect.that(1).isLessThan(2);
        expect.that(1).isLessThanOrEqualTo(2);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutCollectionsAndIterables() {
        final List<String> people = new ArrayList<String>();
        expect.that(people).isEmpty();
        people.add("fred");
        people.add("wilma");
        expect.that(people).isNotEmpty();
        expect.that(people).isOfSize(2);
        expect.that(people).containsItems("fred", "wilma");
        expect.that(people).containsItem("fred");
        expect.that(people).doesNotContainItems("barney", "betty");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutArrays() {
        final String[] greetings = {"hi", "there"};
        expect.that(greetings).isOfSize(2);
        expect.that(greetings).containsItem("hi");
        expect.that(greetings).doesNotContainItem("bye");
        expect.that(greetings).doesNotContainItem(Matchers.greaterThan("zip"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutMaps() {
        final Map<String, String> map = new HashMap<String, String>();
        expect.that(map).isEmpty();
        map.put("key", "value");
        expect.that(map).isNotEmpty();
        expect.that(map).isOfSize(1);
        expect.that(map).containsKey("key");
        expect.that(map).containsValue("value");
        expect.that(map).containsEntry("key", "value");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutDoubles() {
        expect.that(1.1).isCloseTo(1.0, 0.11);
        expect.that(1.1).isNotCloseTo(1.0, 0.1);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutEvents() {
        final Object object = new Object();
        final EventObject myEventObject = new MyEventObject(object);
        expect.that(myEventObject).eventFrom(MyEventObject.class, object);
        expect.that(myEventObject).eventFrom(object);
        expect.that(myEventObject).notEventFrom(new Object());
    }

    private static class MyEventObject extends EventObject {
        private static final long serialVersionUID = -2001716596031438536L;

        private MyEventObject(final Object o) {
            super(o);
        }
    }

    // Xpath stuff from Hamcrest too, but you get the gist ;-)
}
