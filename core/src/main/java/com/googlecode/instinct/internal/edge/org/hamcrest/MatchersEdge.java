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
import org.w3c.dom.Node;

public interface MatchersEdge {
    <T> Matcher<T> is(Matcher<T> param1);

    <T> Matcher<T> is(T param1);

    <T> Matcher<T> is(Class<T> param1);

    <T> Matcher<T> not(Matcher<T> param1);

    <T> Matcher<T> not(T param1);

    <T> Matcher<T> equalTo(T param1);

    <T> Matcher<T> instanceOf(Class<T> param1);

    <T> Matcher<T> allOf(Matcher<T>... param1);

    <T> Matcher<T> allOf(Iterable<Matcher<T>> param1);

    <T> Matcher<T> anyOf(Matcher<T>... param1);

    <T> Matcher<T> anyOf(Iterable<Matcher<T>> param1);

    <T> Matcher<T> sameInstance(T param1);

    <T> Matcher<T> nullValue();

    <T> Matcher<T> notNullValue();

    <T> Matcher<T> describedAs(String param1, Matcher<T> param2, Object... param3);

    <T> Matcher<T[]> hasItemInArray(Matcher<T> param1);

    <T> Matcher<T[]> hasItemInArray(T param1);

    <T> Matcher<Iterable<T>> hasItem(T param1);

    <T> Matcher<Iterable<T>> hasItem(Matcher<T> param1);

    <T> Matcher<Iterable<T>> hasItems(Matcher<T>... param1);

    <T> Matcher<Iterable<T>> hasItems(T... param1);

    <K, V> Matcher<Map<K, V>> hasEntry(Matcher<K> param1, Matcher<V> param2);

    <K, V> Matcher<Map<K, V>> hasEntry(K param1, V param2);

    <K, V> Matcher<Map<K, V>> hasKey(Matcher<K> param1);

    <K, V> Matcher<Map<K, V>> hasKey(K param1);

    <K, V> Matcher<Map<K, V>> hasValue(Matcher<V> param1);

    <K, V> Matcher<Map<K, V>> hasValue(V param1);

    Matcher<Double> closeTo(double param1, double param2);

    <T extends Comparable<T>> Matcher<T> greaterThan(T param1);

    <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T param1);

    <T extends Comparable<T>> Matcher<T> lessThan(T param1);

    <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T param1);

    Matcher<String> equalToIgnoringCase(String param1);

    Matcher<String> equalToIgnoringWhiteSpace(String param1);

    Matcher<String> containsString(String param1);

    Matcher<String> endsWith(String param1);

    Matcher<String> startsWith(String param1);

    <T> Matcher<T> hasToString(Matcher<String> param1);

    <T> Matcher<Class<?>> typeCompatibleWith(Class<T> param1);

    Matcher<EventObject> eventFrom(Class<? extends EventObject> param1, Object param2);

    Matcher<EventObject> eventFrom(Object param1);

    <T> Matcher<T> hasProperty(String param1);

    <T> Matcher<T> hasProperty(String param1, Matcher param2);

    Matcher<Node> hasXPath(String param1, Matcher<String> param2);

    Matcher<Node> hasXPath(String param1);

    <T> Matcher<T> anything();

    <T> Matcher<T> anything(String param1);
}
