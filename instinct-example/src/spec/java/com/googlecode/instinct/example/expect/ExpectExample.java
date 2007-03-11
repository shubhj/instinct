package com.googlecode.instinct.example.expect;

import org.hamcrest.Matchers;
import static com.googlecode.instinct.expect.Expect.expect;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.EventObject;

import junit.framework.TestCase;

/* The examples below illustrate a basic expectation API built on top of the
 * Hamcrest Matcher API.
 * The wrapper objects enable:
 * 1. IDE code completion reduced to the methods that apply to the type object being checked.
 * 2. A more english like (and longer) flow.
 * 3. The standard Hamcrest Matchers or custom Matcher implementations to be
 * passed in as well.
 */
public class ExpectExample extends TestCase {

    public void testShowSomeObjectStuff() {
        expect.that("fred").equalTo("fred");
        expect.that("fred").notEqualTo("barney");
        expect.that(this).sameInstanceAs(this);
        expect.that("fred").notSameInstanceAs("barney");
        expect.that(this).instanceOf(ExpectExample.class);
        expect.that(this).notNull();
        // Mixing with standard Hamcrest matchers
        expect.that("fred").matchesAllOf(Matchers.startsWith("fr"),
                Matchers.containsString("ed"));
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
        List<String> aList = new ArrayList<String>();
        expect.that(aList).isEmpty();
        aList.add("fred");
        aList.add("wilma");
        expect.that(aList).notEmpty();
        expect.that(aList).hasSize(2);
        expect.that(aList).containsItems("fred", "wilma");
        expect.that(aList).containsItem("fred");
        expect.that(aList).notContainItems("barney", "betty");
    }

    public void testShowSomeArrayStuff() {
       String[] strings = {"hi", "there"};
       expect.that(strings).hasLength(2);
       expect.that(strings).containsItem("hi");
       expect.that(strings).notContainItem("bye");
       expect.that(strings).notContainItem(Matchers.greaterThan("zip"));
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
        expect.that(1.1).closeTo(1, 0.11);
        expect.that(1.1).notCloseTo(1, 0.1);
    }


    public void testShowSomeEventStuff() {
        Object object = new Object();
        MyEventObject myEventObject = new MyEventObject(object);
        expect.that(myEventObject).eventFrom(MyEventObject.class, object);
        expect.that(myEventObject).eventFrom(object);
        expect.that(myEventObject).notEventFrom(new Object());
    }

    private static class MyEventObject extends EventObject {
        public MyEventObject(Object o) {
            super(o);
        }
    }

    // Xpath stuff from Hamcrest too, but you get the gist ;-)
}
