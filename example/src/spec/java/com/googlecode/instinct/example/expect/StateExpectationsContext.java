package com.googlecode.instinct.example.expect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;

/**
 * The examples below illustrate Instinct's state-based expectation API.
 *
 * @see com.googlecode.instinct.expect.state.StateExpectations
 */
@SuppressWarnings({"MagicNumber", "unchecked"})
@Context
public final class StateExpectationsContext {

    @Specification
    public void providesMatchersForMakingAssertionsAboutObjects() {
        expect.that("fred").equalTo("fred");
        expect.that("fred").notEqualTo("barney");
        expect.that(this).sameInstanceAs(this);
        expect.that("fred").notSameInstanceAs("barney");
        expect.that(this).instanceOf(StateExpectationsContext.class);
        expect.that(this).notNull();
        // Mixing with standard Hamcrest matchers
        expect.that("fred").matchesAllOf(Matchers.startsWith("fr"), Matchers.containsString("ed"));
        expect.that("fred", Matchers.equalTo("fred"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutClasses() {
        expect.that(String.class).typeCompatibleWith(Comparable.class);
        expect.that(Comparable.class).notTypeCompatibleWith(String.class);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutStrings() {
        expect.that("andersdabeerz").equalToIgnoringCase("AndersDaBeerz");
        expect.that("andersdabeerz").startsWith("anders");
        expect.that("andersdabeerz").containsString("da");
        expect.that("andersdabeerz").endsWith("beerz");
        expect.that("andersdabeerz").equalToIgnoringWhiteSpace(" andersdabeerz ");
        expect.that("andersdabeerz").notContainString("water");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutComparables() {
        expect.that(1).equalTo(1);
        expect.that(1).greaterThan(0);
        expect.that(1).greaterThanOrEqualTo(0);
        expect.that(1).lessThan(2);
        expect.that(1).lessThanOrEqualTo(2);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutCollectionsAndIterables() {
        List<String> people = new ArrayList<String>();
        expect.that(people).isEmpty();
        people.add("fred");
        people.add("wilma");
        expect.that(people).notEmpty();
        expect.that(people).hasSize(2);
        expect.that(people).containsItems("fred", "wilma");
        expect.that(people).containsItem("fred");
        expect.that(people).notContainItems("barney", "betty");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutArrays() {
        String[] greetings = {"hi", "there"};
        expect.that(greetings).hasLength(2);
        expect.that(greetings).containsItem("hi");
        expect.that(greetings).notContainItem("bye");
        expect.that(greetings).notContainItem(Matchers.greaterThan("zip"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutMaps() {
        Map<String, String> map = new HashMap<String, String>();
        expect.that(map).isEmpty();
        map.put("key", "value");
        expect.that(map).notEmpty();
        expect.that(map).hasSize(1);
        expect.that(map).containsKey("key");
        expect.that(map).containsValue("value");
        expect.that(map).containsEntry("key", "value");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutDoubles() {
        expect.that(1.1).closeTo(1.0, 0.11);
        expect.that(1.1).notCloseTo(1.0, 0.1);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutEvents() {
        Object object = new Object();
        EventObject myEventObject = new MyEventObject(object);
        expect.that(myEventObject).eventFrom(MyEventObject.class, object);
        expect.that(myEventObject).eventFrom(object);
        expect.that(myEventObject).notEventFrom(new Object());
    }

    private static class MyEventObject extends EventObject {
        private static final long serialVersionUID = -2001716596031438536L;

        private MyEventObject(Object o) {
            super(o);
        }
    }

    // Xpath stuff from Hamcrest too, but you get the gist ;-)
}
