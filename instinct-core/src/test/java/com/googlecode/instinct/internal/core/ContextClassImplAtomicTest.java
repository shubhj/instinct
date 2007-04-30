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

package com.googlecode.instinct.internal.core;

import java.lang.reflect.Method;
import static java.util.Arrays.asList;
import java.util.Collection;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.isA;
import static com.googlecode.instinct.expect.Mocker.mock;
import static com.googlecode.instinct.expect.Mocker.returnValue;
import static com.googlecode.instinct.expect.Mocker.same;
import com.googlecode.instinct.internal.aggregate.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubjectWithConstructorArgs;

@SuppressWarnings({"unchecked"})
public final class ContextClassImplAtomicTest extends InstinctTestCase {
    private ContextClass contextClass;
    private Class<?> contextType;
    private ContextRunner contextRunner;
    private ContextResult contextResult;
    private Collection<Method> specMethods;
    private Collection<Method> beforeSpecMethods;
    private MarkedMethodLocator methodLocator;

    @Override
    public void setUpTestDoubles() {
        contextType = ASimpleContext.class;
        contextRunner = mock(ContextRunner.class);
        methodLocator = mock(MarkedMethodLocator.class);
        contextResult = mock(ContextResult.class);
        specMethods = asList(getMethod(contextType, "toCheckVerification"));
        beforeSpecMethods = asList(getMethod(contextType, "setUp"), getMethod(contextType, "setUpAgain"));
    }

    @Override
    public void setUpSubject() {
        contextClass = createContextClass(contextType);
    }

    public void testConformsToClassTraits() {
        checkClass(ContextClassImpl.class, ContextClass.class);
    }

    public void testReturnsTypePassedInConstructorFromGetType() {
        expect.that((Object) contextType).sameInstanceAs(contextClass.getType());
    }

    public void testReturnsContextTypeNameSimpleName() {
        expect.that(contextType.getSimpleName()).equalTo(contextClass.getName());
    }

    public void testRunsContextUsingContextRunner() {
        expects(contextRunner).method("run").with(same(contextType)).will(returnValue(contextResult));
        expect.that(contextResult).sameInstanceAs(contextClass.run());
    }

    public void testNotifiesContextRunListenersWhenRun() {
        expectContextRunListenersStored();
        expects(contextRunner).method("run").with(same(contextType)).will(returnValue(contextResult));
        contextClass.run();
    }

    public void testReturnsSpecificationsToRun() {
        expects(methodLocator).method("locateAll").with(same(contextType), isA(MarkingScheme.class)).will(returnValue(specMethods));
        final Collection<SpecificationMethod> methods = contextClass.getSpecificationMethods();
        expect.that(methods).containsItem(specMethod("toCheckVerification"));
    }

    public void testReturnsBeforeSpecificationMethodsToRun() {
        expects(methodLocator).method("locateAll").with(same(contextType), isA(MarkingScheme.class)).will(returnValue(beforeSpecMethods));
        final Collection<SpecificationMethod> methods = contextClass.getSpecificationMethods();
        expect.that(methods).containsItem(specMethod("setUp"));
        expect.that(methods).containsItem(specMethod("setUpAgain"));
    }

    private SpecificationMethod specMethod(final String methodName) {
        return new SpecificationMethodImpl(getMethod(contextType, methodName));
    }

/*
To drive out:
Method[] getBeforeSpecificationMethods();
Method[] getAfterSpecificationMethods();
*/

    @Fix("Replace with Instinct dummy generation.")
    private void expectContextRunListenersStored() {
        for (int i = 0; i < 3; i++) {
            final ContextRunListener contextRunListener = mock(ContextRunListener.class);
            expects(contextRunListener).method("onContext").with(same(contextClass));
            contextClass.addRunListener(contextRunListener);
        }
    }

    private <T> ContextClass createContextClass(final Class<T> contextType) {
        final Object[] constructorArgs = {contextType};
        final Object[] dependencies = {contextRunner, methodLocator};
        return createSubjectWithConstructorArgs(ContextClassImpl.class, constructorArgs, dependencies);
    }
}
