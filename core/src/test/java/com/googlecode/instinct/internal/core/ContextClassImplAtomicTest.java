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
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import static com.googlecode.instinct.internal.util.Reflector.getMethod;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.runner.ContextListener;
import com.googlecode.instinct.runner.SpecificationListener;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import fj.F;
import fj.data.List;
import java.lang.reflect.Method;
import org.jmock.Expectations;

@SuppressWarnings({"unchecked", "UnusedDeclaration", "TypeMayBeWeakened"})
public final class ContextClassImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ContextClass contextClass;
    @Mock private ContextRunner contextRunner;
    @Mock private ContextResult contextResult;
    @Mock private ContextListener contextListener;
    @Mock private SpecificationListener specificationListener;
    @Mock private SpecificationMethodBuilder specificationMethodBuilder;
    private List<Method> specMethods;
    private List<Method> beforeSpecMethods;
    private List<Method> afterSpecMethods;

    @Override
    public void setUpSubject() {
        contextClass = createSubjectWithConstructorArgs(ContextClassImpl.class, new Object[]{ASimpleContext.class}, contextRunner);
    }

    public void testConformsToClassTraits() {
        checkClass(ContextClassImpl.class, ContextClass.class);
    }

    public void testReturnsTypePassedInConstructorFromGetType() {
        expect.that((Object) ASimpleContext.class).isTheSameInstanceAs(contextClass.getType());
    }

    public void testReturnsContextTypeNameSimpleName() {
        expect.that(ASimpleContext.class.getSimpleName()).isEqualTo(contextClass.getName());
    }

    public void testRunsUsingContextRunner() {
        expect.that(new Expectations() {
            {
                one(contextRunner).run(contextClass);
                will(returnValue(contextResult));
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
        final List<SpecificationMethod> specs = contextClass.getSpecificationMethods();
        expect.that(specs).contains(new F<SpecificationMethod, Boolean>() {
            public Boolean f(final SpecificationMethod method) {
                return method.getName().equals("toCheckVerification");
            }
        });
    }

    public void testReturnsBeforeSpecificationMethods() {
        final List<LifecycleMethod> methods = contextClass.getBeforeSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("setUp"));
        expect.that(methods).containsItem(lifecycleMethod("setUpAgain"));
    }

    public void testReturnsAfterSpecificationMethods() {
        final List<LifecycleMethod> methods = contextClass.getAfterSpecificationMethods();
        expect.that(methods).containsItem(lifecycleMethod("tearDown"));
        expect.that(methods).containsItem(lifecycleMethod("tearDownAgain"));
    }

    private LifecycleMethod lifecycleMethod(final String methodName) {
        return new LifecycleMethodImpl(getMethod(ASimpleContext.class, methodName));
    }
}
