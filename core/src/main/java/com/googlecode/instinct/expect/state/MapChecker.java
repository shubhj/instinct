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

package com.googlecode.instinct.expect.state;

import java.util.Map;
import org.hamcrest.Matcher;

public interface MapChecker<K, V> extends ObjectChecker<Map<K, V>> {
    void containsEntry(Matcher<K> matcher, Matcher<V> matcher1);

    void containsEntry(K k, V v);

    void notContainEntry(Matcher<K> matcher, Matcher<V> matcher1);

    void notContainEntry(K k, V v);

    void containsKey(Matcher<K> matcher);

    void containsKey(K k);

    void notContainKey(Matcher<K> matcher);

    void notContainKey(K k);

    void containsValue(Matcher<V> matcher);

    void containsValue(V v);

    void notContainValue(Matcher<V> matcher);

    void notContainValue(V v);

    void isEmpty();

    void notEmpty();

    void hasSize(int size);
}
