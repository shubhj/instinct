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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.expect.check.ArrayChecker;
import com.googlecode.instinct.expect.check.ArrayCheckerImpl;
import com.googlecode.instinct.expect.check.ClassChecker;
import com.googlecode.instinct.expect.check.ClassCheckerImpl;
import com.googlecode.instinct.expect.check.CollectionChecker;
import com.googlecode.instinct.expect.check.CollectionCheckerImpl;
import com.googlecode.instinct.expect.check.ComparableChecker;
import com.googlecode.instinct.expect.check.ComparableCheckerImpl;
import com.googlecode.instinct.expect.check.DoubleChecker;
import com.googlecode.instinct.expect.check.DoubleCheckerImpl;
import com.googlecode.instinct.expect.check.EventObjectChecker;
import com.googlecode.instinct.expect.check.EventObjectCheckerImpl;
import com.googlecode.instinct.expect.check.IterableChecker;
import com.googlecode.instinct.expect.check.IterableCheckerImpl;
import com.googlecode.instinct.expect.check.MapChecker;
import com.googlecode.instinct.expect.check.MapCheckerImpl;
import com.googlecode.instinct.expect.check.NodeChecker;
import com.googlecode.instinct.expect.check.NodeCheckerImpl;
import com.googlecode.instinct.expect.check.ObjectChecker;
import com.googlecode.instinct.expect.check.ObjectCheckerImpl;
import com.googlecode.instinct.expect.check.StringChecker;
import com.googlecode.instinct.expect.check.StringCheckerImpl;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdgeImpl;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.EventObject;
import java.util.Map;

public final class ExpectThatImpl implements ExpectThat {

    private MatcherAssertEdge matcher = new MatcherAssertEdgeImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();

    public <T> ObjectChecker<T> that(T object) {
        return (ObjectChecker<T>) createChecker(ObjectCheckerImpl.class, object);
    }

    public StringChecker that(String string) {
        return createChecker(StringCheckerImpl.class, string);
    }

    public <T extends Comparable<T>> ComparableChecker<T> that(T comparable) {
        return (ComparableChecker<T>) createChecker(ComparableCheckerImpl.class, comparable);
    }

    public <E, T extends Iterable<E>> IterableChecker<E, T> that(T iterable) {
        return (IterableChecker<E, T>) createChecker(IterableCheckerImpl.class, iterable);
    }

    public <E, T extends Collection<E>> CollectionChecker<E, T> that(T collection) {
        return (CollectionChecker<E, T>) createChecker(CollectionCheckerImpl.class, collection);
    }

    public <T> ArrayChecker<T> that(T[] array) {
        return (ArrayChecker<T>) createChecker(ArrayCheckerImpl.class, array);
    }

    public <K, V> MapChecker<K, V> that(Map<K, V> map) {
        return (MapChecker<K, V>) createChecker(MapCheckerImpl.class, map);
    }

    public DoubleChecker that(Double d) {
        return createChecker(DoubleCheckerImpl.class, d);
    }

    public <T> ClassChecker<T> that(Class<T> aClass) {
        return (ClassChecker<T>) createChecker(ClassCheckerImpl.class, aClass);
    }

    public <T extends EventObject> EventObjectChecker<T> that(T eventObject) {
        return (EventObjectChecker<T>) createChecker(EventObjectCheckerImpl.class, eventObject);
    }

    public <T extends Node> NodeChecker<T> that(T node) {
        return (NodeChecker<T>) createChecker(NodeCheckerImpl.class, node);
    }

    public <T> void that(T t, org.hamcrest.Matcher<T> hamcrestMatcher) {
        checkNotNull(t, hamcrestMatcher);
        matcher.expectThat(t, hamcrestMatcher);
    }

    public <T> void notThat(T t, org.hamcrest.Matcher<T> hamcrestMatcher) {
        checkNotNull(t, hamcrestMatcher);
        matcher.expectNotThat(t, hamcrestMatcher);
    }

    private <T> T createChecker(Class<T> checkerClass, Object subject) {
        checkNotNull(checkerClass, subject);
        return objectFactory.create(checkerClass, subject);
    }
}
