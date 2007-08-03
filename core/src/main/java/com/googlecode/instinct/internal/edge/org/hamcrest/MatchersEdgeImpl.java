/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.edge.org.hamcrest;

import java.util.EventObject;
import java.util.Map;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.w3c.dom.Node;

public class MatchersEdgeImpl implements MatchersEdge {
    public final <T> Matcher<T> is(Matcher<T> param1) {
        return Matchers.is(param1);
    }

    public final <T> Matcher<T> is(T param1) {
        return Matchers.is(param1);
    }

    public final <T> Matcher<T> is(Class<T> param1) {
        return Matchers.is(param1);
    }

    public final <T> Matcher<T> not(Matcher<T> param1) {
        return Matchers.not(param1);
    }

    public final <T> Matcher<T> not(T param1) {
        return Matchers.not(param1);
    }

    public final <T> Matcher<T> equalTo(T param1) {
        return Matchers.equalTo(param1);
    }

    public final <T> Matcher<T> instanceOf(Class<T> param1) {
        return Matchers.instanceOf(param1);
    }

    public final <T> Matcher<T> allOf(Matcher<T>... param1) {
        return Matchers.allOf(param1);
    }

    public final <T> Matcher<T> allOf(Iterable<Matcher<T>> param1) {
        return Matchers.allOf(param1);
    }

    public final <T> Matcher<T> anyOf(Matcher<T>... param1) {
        return Matchers.anyOf(param1);
    }

    public final <T> Matcher<T> anyOf(Iterable<Matcher<T>> param1) {
        return Matchers.anyOf(param1);
    }

    public final <T> Matcher<T> sameInstance(T param1) {
        return Matchers.sameInstance(param1);
    }

    public final <T> Matcher<T> anything() {
        return Matchers.anything();
    }

    public final <T> Matcher<T> anything(String param1) {
        return Matchers.anything(param1);
    }

    public final <T> Matcher<T> nullValue() {
        return Matchers.nullValue();
    }

    public final <T> Matcher<T> notNullValue() {
        return Matchers.notNullValue();
    }

    public final <T> Matcher<T> describedAs(String param1, Matcher<T> param2, Object... param3) {
        return Matchers.describedAs(param1, param2, param3);
    }

    public final <T> Matcher<T[]> hasItemInArray(Matcher<T> param1) {
        return Matchers.hasItemInArray(param1);
    }

    public final <T> Matcher<T[]> hasItemInArray(T param1) {
        return Matchers.hasItemInArray(param1);
    }

    public final <T> Matcher<Iterable<T>> hasItem(T param1) {
        return Matchers.hasItem(param1);
    }

    public final <T> Matcher<Iterable<T>> hasItem(Matcher<T> param1) {
        return Matchers.hasItem(param1);
    }

    public final <T> Matcher<Iterable<T>> hasItems(Matcher<T>... param1) {
        return Matchers.hasItems(param1);
    }

    public final <T> Matcher<Iterable<T>> hasItems(T... param1) {
        return Matchers.hasItems(param1);
    }

    public final <K, V> Matcher<Map<K, V>> hasEntry(Matcher<K> param1, Matcher<V> param2) {
        return Matchers.hasEntry(param1, param2);
    }

    public final <K, V> Matcher<Map<K, V>> hasEntry(K param1, V param2) {
        return Matchers.hasEntry(param1, param2);
    }

    public final <K, V> Matcher<Map<K, V>> hasKey(Matcher<K> param1) {
        return Matchers.hasKey(param1);
    }

    public final <K, V> Matcher<Map<K, V>> hasKey(K param1) {
        return Matchers.hasKey(param1);
    }

    public final <K, V> Matcher<Map<K, V>> hasValue(Matcher<V> param1) {
        return Matchers.hasValue(param1);
    }

    public final <K, V> Matcher<Map<K, V>> hasValue(V param1) {
        return Matchers.hasValue(param1);
    }

    public final Matcher<Double> closeTo(double param1, double param2) {
        return Matchers.closeTo(param1, param2);
    }

    public final <T extends Comparable<T>> Matcher<T> greaterThan(T param1) {
        return Matchers.greaterThan(param1);
    }

    public final <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T param1) {
        return Matchers.greaterThanOrEqualTo(param1);
    }

    public final <T extends Comparable<T>> Matcher<T> lessThan(T param1) {
        return Matchers.lessThan(param1);
    }

    public final <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T param1) {
        return Matchers.lessThanOrEqualTo(param1);
    }

    public final Matcher<String> equalToIgnoringCase(String param1) {
        return Matchers.equalToIgnoringCase(param1);
    }

    public final Matcher<String> equalToIgnoringWhiteSpace(String param1) {
        return Matchers.equalToIgnoringWhiteSpace(param1);
    }

    public final Matcher<String> containsString(String param1) {
        return Matchers.containsString(param1);
    }

    public final Matcher<String> endsWith(String param1) {
        return Matchers.endsWith(param1);
    }

    public final Matcher<String> startsWith(String param1) {
        return Matchers.startsWith(param1);
    }

    public final <T> Matcher<T> hasToString(Matcher<String> param1) {
        return Matchers.hasToString(param1);
    }

    public final <T> Matcher<Class<?>> typeCompatibleWith(Class<T> param1) {
        return Matchers.typeCompatibleWith(param1);
    }

    public final Matcher<EventObject> eventFrom(Class<? extends EventObject> param1, Object param2) {
        return Matchers.eventFrom(param1, param2);
    }

    public final Matcher<EventObject> eventFrom(Object param1) {
        return Matchers.eventFrom(param1);
    }

    public final <T> Matcher<T> hasProperty(String param1) {
        return Matchers.hasProperty(param1);
    }

    public final <T> Matcher<T> hasProperty(String param1, Matcher param2) {
        return Matchers.hasProperty(param1, param2);
    }

    public final Matcher<Node> hasXPath(String param1, Matcher<String> param2) {
        return Matchers.hasXPath(param1, param2);
    }

    public final Matcher<Node> hasXPath(String param1) {
        return Matchers.hasXPath(param1);
    }
}
