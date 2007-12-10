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

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.behaviour.Mocker.sequence;
import com.googlecode.instinct.internal.locate.MarkedMethodLocator;
import com.googlecode.instinct.internal.matcher.SpecificationMatcher;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.marker.MarkingScheme;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.Reflector.getMethod;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubjectWithConstructorArgs;
import java.lang.reflect.Method;
import static java.util.Arrays.asList;
import java.util.Collection;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Sequence;

@SuppressWarnings({"unchecked"})
public final class ContextClassImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ContextClass contextClass;
    @Mock private ContextRunner contextRunner;
    @Mock private ContextResult contextResult;
    @Mock private MarkedMethodLocator methodLocator;
    @Mock private ContextListener contextListener;
    @Mock private SpecificationListener specificationListener;
    private Class<?> contextType;
    private Collection<Method> specMethods;
    private Collection<Method> beforeSpecMethods;
    private Collection<Method> afterSpecMethods;

    @Override
    public void setUpTestDoubles() {
        contextType = ASimpleContext.class;
        specMethods = asList(getMethod(contextType, "toCheckVerification"));
        beforeSpecMethods = asList(getMethod(contextType, "setUp"), getMethod(contextType, "setUpAgain"));
        afterSpecMethods = asList(getMethod(contextType, "tearDown"), getMethod(contextType, "tearDownAgain"));
    }

    @Override
    public void setUpSubject() {
        contextClass = createSubjectWithConstructorArgs(ContextClassImpl.class, new Object[]{contextType}, contextRunner, methodLocator);
    }

    public void testConformsToClassTraits() {
        checkClass(ContextClassImpl.class, ContextClass.class);
    }

    public void testReturnsTypePassedInConstructorFromGetType() {
        expect.that((Object) contextType).isTheSameInstanceAs(contextClass.getType());
    }

    public void testReturnsContextTypeNameSimpleName() {
        expect.that(contextType.getSimpleName()).isEqualTo(contextClass.getName());
    }

    public void testRunsUsingContextRunner() {
        expect.that(new Expectations() {
            {
                one(contextRunner).run(contextClass); will(returnValue(contextResult));
            }
        });
        expect.that(contextResult).isTheSameInstanceAs(contextClass.run());
    }

    public void testPassesContextListenersToContextRunner() {
        expect.that(new Expectations() {
            {
                one(contextRunner).addContextListener(contextListener);
            }
        });
        contextClass.addContextListener(contextListener);
    }

    public void testPassesSpecificationListenersToContextRunner() {
        expect.that(new Expectations() {
            {
                one(contextRunner).addSpecificationListener(specificationListener);
            }
        });
        contextClass.addSpecificationListener(specificationListener);
    }

    public void testReturnsSpecificationsToRun() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(methodLocator).locateAll(with(same(contextType)), with(any(MarkingScheme.class))); will(returnValue(specMethods));
            }
        });
        final Collection<LifecycleMethod> methods = contextClass.getSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("toCheckVerification"));
    }

    public void testReturnsBeforeSpecificationMethods() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(methodLocator).locateAll(with(same(contextType)), with(any(MarkingScheme.class))); will(returnValue(beforeSpecMethods));
            }
        });
        final Collection<LifecycleMethod> methods = contextClass.getBeforeSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("setUp"));
        expect.that(methods).containsItem(lifecycleMethod("setUpAgain"));
    }

    public void testReturnsAfterSpecificationMethods() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(methodLocator).locateAll(with(same(contextType)), with(any(MarkingScheme.class))); will(returnValue(afterSpecMethods));
            }
        });
        final Collection<LifecycleMethod> methods = contextClass.getAfterSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("tearDown"));
        expect.that(methods).containsItem(lifecycleMethod("tearDownAgain"));
    }

    public void testReturnsCollectionOfSpecificationMethod() {
        expect.that(new Expectations() {
            {
                final Sequence sequence = sequence();
                one(methodLocator).locateAll(with(same(contextType)), with(any(MarkingScheme.class))); will(returnValue(specMethods)); inSequence(sequence);
                one(methodLocator).locateAll(with(same(contextType)), with(any(MarkingScheme.class))); will(returnValue(beforeSpecMethods)); inSequence(sequence);
                one(methodLocator).locateAll(with(same(contextType)), with(any(MarkingScheme.class))); will(returnValue(afterSpecMethods)); inSequence(sequence);
            }
        });
        final Collection<SpecificationMethod> methods = contextClass.buildSpecificationMethods();
        expect.that(methods).containsItem(specification("toCheckVerification"));
        expect.that(methods.size()).isEqualTo(1);
    }

    private Matcher<SpecificationMethod> specification(final String methodName) {
        return new SpecificationMatcher(methodName);
    }

    private LifecycleMethod lifecycleMethod(final String methodName) {
        return new LifecycleMethodImpl(getMethod(contextType, methodName));
    }
}
