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

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdgeImpl;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.ObjectFactoryImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.io.File;
import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;

@SuppressWarnings({"unchecked", "OverlyCoupledClass"})
public final class StateExpectationsImpl implements StateExpectations {
    private MatcherAssertEdge matcher = new MatcherAssertEdgeImpl();
    private ObjectFactory objectFactory = new ObjectFactoryImpl();

    public <T> ObjectChecker<T> that(final T object) {
        return (ObjectChecker<T>) createChecker(ObjectCheckerImpl.class, object);
    }

    public StringChecker that(final String string) {
        return createChecker(StringCheckerImpl.class, string);
    }

    public <T extends Comparable<T>> ComparableChecker<T> that(final T comparable) {
        return (ComparableChecker<T>) createChecker(ComparableCheckerImpl.class, comparable);
    }

    public <E, T extends Iterable<E>> IterableChecker<E, T> that(final T iterable) {
        return (IterableChecker<E, T>) createChecker(IterableCheckerImpl.class, iterable);
    }

    public <E, T extends Collection<E>> CollectionChecker<E, T> that(final T collection) {
        return (CollectionChecker<E, T>) createChecker(CollectionCheckerImpl.class, collection);
    }

    public <T> ArrayChecker<T> that(final T[] array) {
        return (ArrayChecker<T>) createChecker(ArrayCheckerImpl.class, array);
    }

    public <K, V> MapChecker<K, V> that(final Map<K, V> map) {
        return (MapChecker<K, V>) createChecker(MapCheckerImpl.class, map);
    }

    public DoubleChecker that(final Double d) {
        return createChecker(DoubleCheckerImpl.class, d);
    }

    public BooleanChecker that(final Boolean b) {
        return createChecker(BooleanCheckerImpl.class, b);
    }

    public <T> ClassChecker<T> that(final Class<T> aClass) {
        return (ClassChecker<T>) createChecker(ClassCheckerImpl.class, aClass);
    }

    public <T extends EventObject> EventObjectChecker<T> that(final T eventObject) {
        return (EventObjectChecker<T>) createChecker(EventObjectCheckerImpl.class, eventObject);
    }

    public <T extends Node> NodeChecker<T> that(final T node) {
        return (NodeChecker<T>) createChecker(NodeCheckerImpl.class, node);
    }

    public FileChecker that(final File file) {
        return createChecker(FileCheckerImpl.class, file);
    }

    public <T> void that(final T t, final Matcher<T> hamcrestMatcher) {
        checkNotNull(hamcrestMatcher);
        matcher.expectThat(t, hamcrestMatcher);
    }

    public <T> void notThat(final T t, final Matcher<T> hamcrestMatcher) {
        checkNotNull(t, hamcrestMatcher);
        matcher.expectNotThat(t, hamcrestMatcher);
    }

    private <T> T createChecker(final Class<T> checkerClass, final Object subject) {
        return objectFactory.create(checkerClass, subject);
    }
}
