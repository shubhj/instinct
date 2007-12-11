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

package com.googlecode.instinct.expect;

import com.googlecode.instinct.expect.behaviour.BehaviourExpectations;
import com.googlecode.instinct.expect.behaviour.BehaviourExpectationsImpl;
import com.googlecode.instinct.expect.state.checker.ArrayChecker;
import com.googlecode.instinct.expect.state.checker.BooleanChecker;
import com.googlecode.instinct.expect.state.checker.ClassChecker;
import com.googlecode.instinct.expect.state.checker.CollectionChecker;
import com.googlecode.instinct.expect.state.checker.ComparableChecker;
import com.googlecode.instinct.expect.state.checker.DoubleChecker;
import com.googlecode.instinct.expect.state.checker.EventObjectChecker;
import com.googlecode.instinct.expect.state.checker.FileChecker;
import com.googlecode.instinct.expect.state.checker.IterableChecker;
import com.googlecode.instinct.expect.state.checker.MapChecker;
import com.googlecode.instinct.expect.state.checker.NodeChecker;
import com.googlecode.instinct.expect.state.checker.ObjectChecker;
import com.googlecode.instinct.expect.state.StateExpectations;
import com.googlecode.instinct.expect.state.StateExpectationsImpl;
import com.googlecode.instinct.expect.state.checker.StringChecker;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.io.File;
import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import org.hamcrest.Matcher;
import org.jmock.internal.ExpectationBuilder;
import org.w3c.dom.Node;

@Fix({"Use the new interface composer to compose these interfaces."})
public final class ExpectThatImpl implements ExpectThat {
    private StateExpectations stateExpectations = new StateExpectationsImpl();
    private BehaviourExpectations behaviourExpectations = new BehaviourExpectationsImpl();

    public <T> ObjectChecker<T> that(final T object) {
        return stateExpectations.that(object);
    }

    public StringChecker that(final String string) {
        return stateExpectations.that(string);
    }

    public <T extends Comparable<T>> ComparableChecker<T> that(final T comparable) {
        return stateExpectations.that(comparable);
    }

    public <E, T extends Iterable<E>> IterableChecker<E, T> that(final T iterable) {
        return stateExpectations.that(iterable);
    }

    public <E, T extends Collection<E>> CollectionChecker<E, T> that(final T collection) {
        return stateExpectations.that(collection);
    }

    public <T> ArrayChecker<T> that(final T[] array) {
        return stateExpectations.that(array);
    }

    public <K, V> MapChecker<K, V> that(final Map<K, V> map) {
        return stateExpectations.that(map);
    }

    public DoubleChecker that(final Double d) {
        return stateExpectations.that(d);
    }

    public BooleanChecker that(final Boolean b) {
        return stateExpectations.that(b);
    }

    public <T> ClassChecker<T> that(final Class<T> aClass) {
        return stateExpectations.that(aClass);
    }

    public <T extends EventObject> EventObjectChecker<T> that(final T eventObject) {
        return stateExpectations.that(eventObject);
    }

    public <T extends Node> NodeChecker<T> that(final T node) {
        return stateExpectations.that(node);
    }

    public FileChecker that(final File file) {
        return stateExpectations.that(file);
    }

    public <T> void that(final T t, final Matcher<T> hamcrestMatcher) {
        checkNotNull(hamcrestMatcher);
        stateExpectations.that(hamcrestMatcher);
    }

    public <T> void notThat(final T t, final Matcher<T> hamcrestMatcher) {
        checkNotNull(hamcrestMatcher);
        stateExpectations.that(hamcrestMatcher);
    }

    public void that(final ExpectationBuilder expectations) {
        checkNotNull(expectations);
        behaviourExpectations.that(expectations);
    }
}
