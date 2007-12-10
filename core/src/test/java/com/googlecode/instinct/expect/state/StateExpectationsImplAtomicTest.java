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

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClassWithoutParamChecks;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;
import org.hamcrest.Matcher;
import org.jmock.Expectations;

@SuppressWarnings({"unchecked", "RawUseOfParameterizedType", "OverlyCoupledClass"})
public final class StateExpectationsImplAtomicTest extends InstinctTestCase {
    @Subject private StateExpectations stateExpectations;
    @Mock private ObjectFactory objectFactory;
    @Mock private MatcherAssertEdge matcherAssert;
    @Mock private Matcher hamcrestMatcher;
    @Stub(auto = false) private Collection<String> collection;
    @Dummy private Object object;

    @Override
    public void setUpTestDoubles() {
        collection = new ArrayList<String>();
    }

    @Override
    public void setUpSubject() {
        stateExpectations = new StateExpectationsImpl();
        insertFieldValue(stateExpectations, ObjectFactory.class, objectFactory);
        insertFieldValue(stateExpectations, MatcherAssertEdge.class, matcherAssert);
    }

    public void testConformsToClassTraits() {
        checkClassWithoutParamChecks(StateExpectationsImpl.class, StateExpectations.class);
    }

    public void testCalledWithObjectReturnsObjectChecker() {
        final ObjectChecker expectedChecker = expectCheckerCreated(ObjectChecker.class, ObjectCheckerImpl.class, object);
        final ObjectChecker objectChecker = stateExpectations.that(object);
        assertSame(expectedChecker, objectChecker);
    }

    public void testCalledWithStringReturnsStringChecker() {
        final String string = "hi!";
        final ObjectChecker expectedChecker = expectCheckerCreated(StringChecker.class, StringCheckerImpl.class, string);
        final ObjectChecker checker = stateExpectations.that(string);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithComparableReturnsComparableChecker() {
        final Integer integer = 1;
        final ObjectChecker expectedChecker =
                expectCheckerCreated(ComparableChecker.class, ComparableCheckerImpl.class, integer);
        final ObjectChecker checker = stateExpectations.that(integer);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithIterableReturnsIterableChecker() {
        final ObjectChecker expectedChecker = expectCheckerCreated(IterableChecker.class, IterableCheckerImpl.class, collection);
        final ObjectChecker checker = stateExpectations.that((Iterable<String>) collection);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithCollectionReturnsCollectionChecker() {
        final ObjectChecker expectedChecker = expectCheckerCreated(CollectionChecker.class, CollectionCheckerImpl.class, collection);
        final ObjectChecker checker = stateExpectations.that(collection);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithArrayReturnsArrayChecker() {
        final String[] array = {"hi again!"};
        final ObjectChecker expectedChecker = expectCheckerCreated(ArrayChecker.class, ArrayCheckerImpl.class, array);
        final ObjectChecker checker = stateExpectations.that(array);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithMapReturnsMapChecker() {
        final Map<String, String> map = new HashMap<String, String>();
        final ObjectChecker expectedChecker = expectCheckerCreated(MapChecker.class, MapCheckerImpl.class, map);
        final ObjectChecker checker = stateExpectations.that(map);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithDoubleReturnsDoubleChecker() {
        final Double dubble = 0.0;
        final ObjectChecker expectedChecker = expectCheckerCreated(DoubleChecker.class, DoubleCheckerImpl.class, dubble);
        final ObjectChecker checker = stateExpectations.that(dubble);
        assertSame(expectedChecker, checker);
    }

    public void testCalledClassWithReturnsClassChecker() {
        final Class<?> type = String.class;
        final ObjectChecker expectedChecker = expectCheckerCreated(ClassChecker.class, ClassCheckerImpl.class, type);
        final ObjectChecker checker = stateExpectations.that(type);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithEventObjectReturnsEventObjectChecker() {
        final EventObject eventObject = new EventObject(object);
        final ObjectChecker expectedChecker = expectCheckerCreated(EventObjectChecker.class, EventObjectCheckerImpl.class, eventObject);
        final ObjectChecker checker = stateExpectations.that(eventObject);
        assertSame(expectedChecker, checker);
    }

    public void testMethodThatDelegatesToMatcherCorrectly() {
        expect.that(new Expectations() {
            {
                one(matcherAssert).expectThat(object, hamcrestMatcher);
            }
        });
        stateExpectations.that(object, hamcrestMatcher);
    }

    public void testMethodNotThatDelegatesToMatcherCorrectly() {
        expect.that(new Expectations() {
            {
                one(matcherAssert).expectNotThat(object, hamcrestMatcher);
            }
        });
        stateExpectations.notThat(object, hamcrestMatcher);
    }

    private <T, I extends ObjectChecker<T>, C extends ObjectCheckerImpl<T>> I expectCheckerCreated(
            final Class<I> checkerInterfaceClass, final Class<C> checkerImplClass, final T checkerSubject) {
        final I expectedChecker = mock(checkerInterfaceClass);
        expect.that(new Expectations() {
            {
                one(objectFactory).create(checkerImplClass, checkerSubject); will(returnValue(expectedChecker));
            }
        });
        return expectedChecker;
    }
}
