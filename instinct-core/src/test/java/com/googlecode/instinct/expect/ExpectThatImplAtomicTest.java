package com.googlecode.instinct.expect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
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
import com.googlecode.instinct.expect.check.ObjectChecker;
import com.googlecode.instinct.expect.check.ObjectCheckerImpl;
import com.googlecode.instinct.expect.check.StringChecker;
import com.googlecode.instinct.expect.check.StringCheckerImpl;
import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdge;
import com.googlecode.instinct.internal.util.ObjectFactory;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.same;
import static com.googlecode.instinct.mock.Mocker.sameElements;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.Reflector.insertFieldValue;
import org.hamcrest.Matcher;

@Suggest("Change name to StateExpectations.")
@SuppressWarnings({"unchecked", "RawUseOfParameterizedType", "OverlyCoupledClass"})
public final class ExpectThatImplAtomicTest extends InstinctTestCase {
    private ObjectFactory objectFactory;
    private MatcherAssertEdge matcherAssert;
    private ExpectThat expectThat;
    private Matcher hamcrestMatcher;
    private Collection<String> collection;
    private Object object;

    public void testClassProperties() {
        // TODO get this working - fix class checker
        //checkClass(ExpectThatImpl.class, ExpectThat.class);
    }

    public void testCalledWithObjectReturnsObjectChecker() {
        ObjectChecker expectedChecker =
                expectCheckerCreated(ObjectChecker.class, ObjectCheckerImpl.class, object);
        ObjectChecker objectChecker = expectThat.that(object);
        assertSame(expectedChecker, objectChecker);
    }

    public void testCalledWithStringReturnsStringChecker() {
        String string = "hi!";
        ObjectChecker expectedChecker =
                expectCheckerCreated(StringChecker.class, StringCheckerImpl.class, string);
        ObjectChecker checker = expectThat.that(string);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithComparableReturnsComparableChecker() {
        Integer integer = 1;
        ObjectChecker expectedChecker =
                expectCheckerCreated(ComparableChecker.class, ComparableCheckerImpl.class, integer);
        ObjectChecker checker = expectThat.that(integer);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithIterableReturnsIterableChecker() {
        ObjectChecker expectedChecker =
                expectCheckerCreated(IterableChecker.class, IterableCheckerImpl.class, collection);
        ObjectChecker checker = expectThat.that((Iterable<String>) collection);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithCollectionReturnsCollectionChecker() {
        ObjectChecker expectedChecker =
                expectCheckerCreated(CollectionChecker.class, CollectionCheckerImpl.class, collection);
        ObjectChecker checker = expectThat.that(collection);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithArrayReturnsArrayChecker() {
        String[] array = {"hi again!"};
        ObjectChecker expectedChecker =
                expectCheckerCreated(ArrayChecker.class, ArrayCheckerImpl.class, array);
        ObjectChecker checker = expectThat.that(array);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithMapReturnsMapChecker() {
        Map<String, String> map = new HashMap<String, String>();
        ObjectChecker expectedChecker =
                expectCheckerCreated(MapChecker.class, MapCheckerImpl.class, map);
        ObjectChecker checker = expectThat.that(map);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithDoubleReturnsDoubleChecker() {
        Double dubble = 0.0;
        ObjectChecker expectedChecker =
                expectCheckerCreated(DoubleChecker.class, DoubleCheckerImpl.class, dubble);
        ObjectChecker checker = expectThat.that(dubble);
        assertSame(expectedChecker, checker);
    }

    public void testCalledClassWithReturnsClassChecker() {
        Class<?> type = String.class;
        ObjectChecker expectedChecker =
                expectCheckerCreated(ClassChecker.class, ClassCheckerImpl.class, type);
        ObjectChecker checker = expectThat.that(type);
        assertSame(expectedChecker, checker);
    }

    public void testCalledWithEventObjectReturnsEventObjectChecker() {
        EventObject eventObject = new EventObject(object);
        ObjectChecker expectedChecker =
                expectCheckerCreated(EventObjectChecker.class, EventObjectCheckerImpl.class, eventObject);
        ObjectChecker checker = expectThat.that(eventObject);
        assertSame(expectedChecker, checker);
    }

    public void testMethodThatDelegatesToMatcherCorrectly() {
        expects(matcherAssert).method("expectThat").with(same(object), same(hamcrestMatcher));
        expectThat.that(object, hamcrestMatcher);
    }

    public void testMethodNotThatDelegatesToMatcherCorrectly() {
        expects(matcherAssert).method("expectNotThat").with(same(object), same(hamcrestMatcher));
        expectThat.notThat(object, hamcrestMatcher);
    }

    private <T, I extends ObjectChecker<T>, C extends ObjectCheckerImpl<T>> I expectCheckerCreated(
            Class<I> checkerInterfaceClass, Class<C> checkerImplClass, T checkerSubject) {
        I expectedChecker = mock(checkerInterfaceClass);
        expects(objectFactory).method("create")
                .with(same(checkerImplClass), sameElements(new Object[]{checkerSubject}))
                .will(returnValue(expectedChecker));
        return expectedChecker;
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
        expectThat = new ExpectThatImpl();
        insertFieldValue(expectThat, ObjectFactory.class, objectFactory);
        insertFieldValue(expectThat, MatcherAssertEdge.class, matcherAssert);
    }
}
