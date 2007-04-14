/*
 * Copyright 2006-2007 Ben Warren
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

package com.googlecode.instinct.expect;

import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import com.googlecode.instinct.expect.behaviour.BehaviourExpectations;
import com.googlecode.instinct.expect.behaviour.BehaviourExpectationsImpl;
import com.googlecode.instinct.expect.state.ArrayChecker;
import com.googlecode.instinct.expect.state.ClassChecker;
import com.googlecode.instinct.expect.state.CollectionChecker;
import com.googlecode.instinct.expect.state.ComparableChecker;
import com.googlecode.instinct.expect.state.DoubleChecker;
import com.googlecode.instinct.expect.state.EventObjectChecker;
import com.googlecode.instinct.expect.state.IterableChecker;
import com.googlecode.instinct.expect.state.MapChecker;
import com.googlecode.instinct.expect.state.NodeChecker;
import com.googlecode.instinct.expect.state.ObjectChecker;
import com.googlecode.instinct.expect.state.StateExpectations;
import com.googlecode.instinct.expect.state.StateExpectationsImpl;
import com.googlecode.instinct.expect.state.StringChecker;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;

@Fix({"Add javadoc"})
public final class ExpectThatImpl implements ExpectThat {
    private StateExpectations stateExpectations = new StateExpectationsImpl();
    private BehaviourExpectations behaviourExpectations = new BehaviourExpectationsImpl();

    public <T> ObjectChecker<T> that(final T object) {
        checkNotNull(object);
        return stateExpectations.that(object);
    }

    public StringChecker that(final String string) {
        checkNotWhitespace(string);
        return null;
    }

    public <T extends Comparable<T>> ComparableChecker<T> that(final T comparable) {
        checkNotNull(comparable);
        return null;
    }

    public <E, T extends Iterable<E>> IterableChecker<E, T> that(final T iterable) {
        checkNotNull(iterable);
        return null;
    }

    public <E, T extends Collection<E>> CollectionChecker<E, T> that(final T collection) {
        checkNotNull(collection);
        return null;
    }

    public <T> ArrayChecker<T> that(final T[] array) {
        checkNotNull(array);
        return null;
    }

    public <K, V> MapChecker<K, V> that(final Map<K, V> map) {
        checkNotNull(map);
        return null;
    }

    public DoubleChecker that(final Double d) {
        checkNotNull(d);
        return null;
    }

    public <T> ClassChecker<T> that(final Class<T> aClass) {
        checkNotNull(aClass);
        return null;
    }

    public <T extends EventObject> EventObjectChecker<T> that(final T eventObject) {
        checkNotNull(eventObject);
        return null;
    }

    public <T extends Node> NodeChecker<T> that(final T node) {
        checkNotNull(node);
        return null;
    }

    public <T> void that(final T t, final Matcher<T> hamcrestMatcher) {
        checkNotNull(t, hamcrestMatcher);
    }

    public <T> void notThat(final T t, final Matcher<T> hamcrestMatcher) {
        checkNotNull(t, hamcrestMatcher);
    }
}
