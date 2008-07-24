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

import com.googlecode.instinct.expect.state.checker.ArrayChecker;
import com.googlecode.instinct.expect.state.checker.BooleanChecker;
import com.googlecode.instinct.expect.state.checker.ClassChecker;
import com.googlecode.instinct.expect.state.checker.CollectionChecker;
import com.googlecode.instinct.expect.state.checker.ComparableChecker;
import com.googlecode.instinct.expect.state.checker.DoubleChecker;
import com.googlecode.instinct.expect.state.checker.EventObjectChecker;
import com.googlecode.instinct.expect.state.checker.FileChecker;
import com.googlecode.instinct.expect.state.checker.FjListChecker;
import com.googlecode.instinct.expect.state.checker.IterableChecker;
import com.googlecode.instinct.expect.state.checker.MapChecker;
import com.googlecode.instinct.expect.state.checker.NodeChecker;
import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.expect.state.checker.StringChecker;
import com.googlecode.instinct.internal.util.Fix;
import fj.data.List;
import java.io.File;
import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;

/**
 * State-based expectation API. State expectations are expectations on the state of an object, and are similar to JUnit's Assert capabilities. The
 * instinct state-based expectation API is built on top of the Hamcrest Matcher API. The Instinct wrapper methods enable: <ol> <li>IDE code completion
 * reduced to the methods that apply to the type object being checked.</li> <li>A more English like (and longer) flow. </li> <li>The standard Hamcrest
 * Matchers or custom Matcher implementations to be passed in as well.</li> </ol>
 */
@Fix({"Add javadoc"})
public interface StateExpectations {
    <T> ObjectChecker<T> that(T object);

    StringChecker that(String string);

    <T extends Comparable<T>> ComparableChecker<T> that(T comparable);

    <E, T extends Iterable<E>> IterableChecker<E, T> that(T iterable);

    <E, T extends Collection<E>> CollectionChecker<E, T> that(T collection);

    <T> ArrayChecker<T> that(T[] array);

    <T> FjListChecker<T> that(List<T> list);

    <K, V> MapChecker<K, V> that(Map<K, V> map);

    DoubleChecker that(Double d);

    BooleanChecker that(Boolean b);

    <T> ClassChecker<T> that(Class<T> aClass);

    <T extends EventObject> EventObjectChecker<T> that(T eventObject);

    <T extends Node> NodeChecker<T> that(T node);

    FileChecker that(File file);

    <T> void that(T t, Matcher<T> hamcrestMatcher);

    <T> void notThat(T t, Matcher<T> hamcrestMatcher);
}
