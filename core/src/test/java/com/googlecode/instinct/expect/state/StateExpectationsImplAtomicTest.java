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

import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import static com.googlecode.instinct.expect.Mocker12.same;
import static com.googlecode.instinct.expect.Mocker12.sameElements;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matcher;

@SuppressWarnings({"unchecked", "RawUseOfParameterizedType", "OverlyCoupledClass"})
public final class StateExpectationsImplAtomicTest extends InstinctTestCase {
    private ObjectFactory objectFactory;
    private MatcherAssertEdge matcherAssert;
    private StateExpectations stateExpectations;
    private Matcher hamcrestMatcher;
    private Collection<String> collection;
    private Object object;

    public void testConformsToClassTraits() {
        // TODO get this working - fix class checker
        //checkClass(StateExpectationsImpl.class, StateExpectations.class);
    }

    public void testCalledWithObjectReturnsObjectChecker() {
        ObjectChecker expectedChecker =
                expectCheckerCreated(ObjectChecker.class, ObjectCheckerImpl.class, object);
        ObjectChecker objectChecker = stateExpectations.that(object);
        assertSame(expectedChecker, objectChecker);
    }

    public void testCalledWithStringReturnsStringChecker() {
        String string = "hi!";
        ObjectChecker expectedChecker =
                expectCheckerCreated(StringChecker.class, StringCheckerImpl.class, string);
        ObjectChecker checker = stateExpectations.that(string);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithComparableReturnsComparableChecker() {
        Integer integer = 1;
        ObjectChecker expectedChecker =
                expectCheckerCreated(ComparableChecker.class, ComparableCheckerImpl.class, integer);
        ObjectChecker checker = stateExpectations.that(integer);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithIterableReturnsIterableChecker() {
        ObjectChecker expectedChecker =
                expectCheckerCreated(IterableChecker.class, IterableCheckerImpl.class, collection);
        ObjectChecker checker = stateExpectations.that((Iterable<String>) collection);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithCollectionReturnsCollectionChecker() {
        ObjectChecker expectedChecker =
                expectCheckerCreated(CollectionChecker.class, CollectionCheckerImpl.class, collection);
        ObjectChecker checker = stateExpectations.that(collection);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithArrayReturnsArrayChecker() {
        String[] array = {"hi again!"};
        ObjectChecker expectedChecker =
                expectCheckerCreated(ArrayChecker.class, ArrayCheckerImpl.class, array);
        ObjectChecker checker = stateExpectations.that(array);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithMapReturnsMapChecker() {
        Map<String, String> map = new HashMap<String, String>();
        ObjectChecker expectedChecker =
                expectCheckerCreated(MapChecker.class, MapCheckerImpl.class, map);
        ObjectChecker checker = stateExpectations.that(map);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithDoubleReturnsDoubleChecker() {
        Double dubble = 0.0;
        ObjectChecker expectedChecker =
                expectCheckerCreated(DoubleChecker.class, DoubleCheckerImpl.class, dubble);
        ObjectChecker checker = stateExpectations.that(dubble);
        assertSame(expectedChecker, checker);
    }

    public void testCalledClassWithReturnsClassChecker() {
        Class<?> type = String.class;
        ObjectChecker expectedChecker =
                expectCheckerCreated(ClassChecker.class, ClassCheckerImpl.class, type);
        ObjectChecker checker = stateExpectations.that(type);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithEventObjectReturnsEventObjectChecker() {
        EventObject eventObject = new EventObject(object);
        ObjectChecker expectedChecker =
                expectCheckerCreated(EventObjectChecker.class, EventObjectCheckerImpl.class, eventObject);
        ObjectChecker checker = stateExpectations.that(eventObject);
        assertSame(expectedChecker, checker);
    }

    public void testMethodThatDelegatesToMatcherCorrectly() {
        expects(matcherAssert).method("expectThat").with(same(object), same(hamcrestMatcher));
        stateExpectations.that(object, hamcrestMatcher);
    }

    public void testMethodNotThatDelegatesToMatcherCorrectly() {
        expects(matcherAssert).method("expectNotThat").with(same(object), same(hamcrestMatcher));
        stateExpectations.notThat(object, hamcrestMatcher);
    }

    @Override
    public void setUpTestDoubles() {
        objectFactory = mock(ObjectFactory.class);
        matcherAssert = mock(MatcherAssertEdge.class);
        hamcrestMatcher = mock(Matcher.class);
        collection = new ArrayList<String>();
        object = new Object();
    }

    @Override
    public void setUpSubject() {
        stateExpectations = new StateExpectationsImpl();
        insertFieldValue(stateExpectations, ObjectFactory.class, objectFactory);
        insertFieldValue(stateExpectations, MatcherAssertEdge.class, matcherAssert);
    }

    private <T, I extends ObjectChecker<T>, C extends ObjectCheckerImpl<T>> I expectCheckerCreated(
            Class<I> checkerInterfaceClass, Class<C> checkerImplClass, T checkerSubject) {
        I expectedChecker = mock(checkerInterfaceClass);
        expects(objectFactory).method("create")
                .with(same(checkerImplClass), sameElements(new Object[]{checkerSubject}))
                .will(returnValue(expectedChecker));
        return expectedChecker;
    }
}
