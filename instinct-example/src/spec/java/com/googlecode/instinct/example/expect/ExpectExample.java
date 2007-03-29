package com.googlecode.instinct.example.expect;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.TestCase;
import org.hamcrest.Matchers;

/**
 * The examples below illustrate a basic expectation API built on top of the Hamcrest Matcher API.
 * The wrapper objects enable:
 * 1. IDE code completion reduced to the methods that apply to the type object being checked.
 * 2. A more english like (and longer) flow.
 * 3. The standard Hamcrest Matchers or custom Matcher implementations to be passed in as well.
 */
@SuppressWarnings({"MagicNumber", "unchecked"})
@Suggest("Move to be a BehaviourContext")
public final class ExpectExample extends TestCase {

    public void testShowSomeObjectStuff() {
        expect.that("fred").equalTo("fred");
        expect.that("fred").notEqualTo("barney");
        expect.that(this).sameInstanceAs(this);
        expect.that("fred").notSameInstanceAs("barney");
        expect.that(this).instanceOf(ExpectExample.class);
        expect.that(this).notNull();
        // Mixing with standard Hamcrest matchers
        expect.that("fred").matchesAllOf(Matchers.startsWith("fr"), Matchers.containsString("ed"));
        expect.that("fred", Matchers.equalTo("fred"));
    }

    public void testShowSomeClassStuff() {
        expect.that(String.class).typeCompatibleWith(Comparable.class);
        expect.that(Comparable.class).notTypeCompatibleWith(String.class);
    }

    public void testShowSomeStringStuff() {
        expect.that("andersdabeerz").equalsIgnoringCase("AndersDaBeerz");
        expect.that("andersdabeerz").startsWith("anders");
        expect.that("andersdabeerz").containsString("da");
        expect.that("andersdabeerz").endsWith("beerz");
        expect.that("andersdabeerz").equalsIgnoringWhiteSpace(" andersdabeerz ");
        expect.that("andersdabeerz").notContainString("water");
    }

    public void testShowSomeComparableStuff() {
        expect.that(1).equalTo(1);
        expect.that(1).greaterThan(0);
        expect.that(1).greaterThanOrEqualTo(0);
        expect.that(1).lessThan(2);
        expect.that(1).lessThanOrEqualTo(2);
    }

    public void testShowSomeCollectionAndIterableStuff() {
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

    public void testShowSomeArrayStuff() {
        String[] greatings = {"hi", "there"};
        expect.that(greatings).hasLength(2);
        expect.that(greatings).containsItem("hi");
        expect.that(greatings).notContainItem("bye");
        expect.that(greatings).notContainItem(Matchers.greaterThan("zip"));
    }

    public void testShowSomeMapStuff() {
        Map<String, String> map = new HashMap<String, String>();
        expect.that(map).isEmpty();
        map.put("key", "value");
        expect.that(map).notEmpty();
        expect.that(map).hasSize(1);
        expect.that(map).containsKey("key");
        expect.that(map).containsValue("value");
        expect.that(map).containsEntry("key", "value");
    }

    public void testShowDoubleStuff() {
        expect.that(1.1).closeTo(1.0, 0.11);
        expect.that(1.1).notCloseTo(1.0, 0.1);
    }

    public void testShowSomeEventStuff() {
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
