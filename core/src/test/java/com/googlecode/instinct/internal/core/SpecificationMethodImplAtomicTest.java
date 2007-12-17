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
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import static com.googlecode.instinct.internal.util.Reflector.getMethod;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.runner.SpecificationListener;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.actor.TestSubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import org.jmock.Expectations;

@SuppressWarnings({"unchecked"})
@Suggest({"Todo:", "Add a run method, pass a spec runner as a dependency, pass in other methods required for runners."})
public final class SpecificationMethodImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private SpecificationMethod specificationMethod;
    @Mock private SpecificationRunner specificationRunner;
    @Mock private LifecycleMethod specMethod;
    @Mock private SpecificationResult specificationResult;
    @Mock private Collection<LifecycleMethod> beforeSpecMethods;
    @Mock private Collection<LifecycleMethod> afterSpecMethods;
    @Stub private String methodName;
    @Stub private Class<?> declaringClass;
    @Dummy(auto = false) private Method annotatedSpec;
    @Dummy(auto = false) private Method annotatedFailingSpec;
    @Dummy(auto = false) private Method annotatedFailingSpecWithMessage;
    @Dummy(auto = false) private Method nonAnnotatedSpec;
    @Dummy(auto = false) private Method notASpec;
    @Dummy(auto = false) private Method pendingSpec;

    @Override
    public void setUpTestDoubles() {
        annotatedSpec = getMethod(ContextWithSpecificationsMarkedInDifferentWays.class, "annotatedSpecificationMethod");
        annotatedFailingSpec = getMethod(ContextWithSpecificationsMarkedInDifferentWays.class, "annotatedSpecificationMethodThatShouldFail");
        annotatedFailingSpecWithMessage =
                getMethod(ContextWithSpecificationsMarkedInDifferentWays.class, "annotatedSpecificationMethodThatShouldFailWithAMessage");
        nonAnnotatedSpec = getMethod(ContextWithSpecificationsMarkedInDifferentWays.class, "shouldBeAspecificationThatIsNotAnnotated");
        notASpec = getMethod(ContextWithSpecificationsMarkedInDifferentWays.class, "isNotASpecification");
        pendingSpec = getMethod(ContextWithSpecificationsMarkedInDifferentWays.class, "pendingSpecification");
    }

    @Override
    public void setUpSubject() {
        final Object[] constructorArgs = {specMethod, beforeSpecMethods, afterSpecMethods};
        specificationMethod = createSubjectWithConstructorArgs(SpecificationMethodImpl.class, constructorArgs, specificationRunner);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationMethodImpl.class, SpecificationMethod.class);
    }

    public void testPassesSpecificationListenersToSpecificationRunner() {
        final SpecificationListener specificationListener = mock(SpecificationListener.class);
        expect.that(new Expectations() {
            {
                atLeast(1).of(specificationRunner).addSpecificationListener(specificationListener);
            }
        });
        specificationMethod.addSpecificationListener(specificationListener);
    }

    public void testRunsSpecificationUsingASpecificationRunner() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specificationRunner).run(specificationMethod);
                will(returnValue(specificationResult));
            }
        });
        final SpecificationResult result = specificationMethod.run();
        expect.that(result).isTheSameInstanceAs(specificationResult);
    }

    public void testReturnsUnderlyingSpecificationMethod() {
        final LifecycleMethod returnedSpecMethod = specificationMethod.getSpecificationMethod();
        expect.that(returnedSpecMethod).isTheSameInstanceAs(specMethod);
    }

    public void testReturnsUnderlyingBeforeSpecMethods() {
        final Collection<LifecycleMethod> returnedBeforeSpecMethods = specificationMethod.getBeforeSpecificationMethods();
        expect.that(returnedBeforeSpecMethods).isTheSameInstanceAs(beforeSpecMethods);
    }

    public void testReturnsUnderlyingAfterSpecMethods() {
        final Collection<LifecycleMethod> returnedAfterSpecMethods = specificationMethod.getAfterSpecificationMethods();
        expect.that(returnedAfterSpecMethods).isTheSameInstanceAs(afterSpecMethods);
    }

    public void testReturnsDeclaringClassOfLifecycleMethod() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getContextClass();
                will(returnValue(declaringClass));
            }
        });
        assertEquals(declaringClass, specificationMethod.getContextClass());
    }

    public void testReturnsNameFromUnderlyingLifecycleMethod() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getName();
                will(returnValue(methodName));
            }
        });
        expect.that(specificationMethod.getName()).isEqualTo(methodName);
    }

    public void testReturnsParameterAnnotationsFromUnderlyingLifecycleMethod() {
        final Annotation[][] fakeAnnotations = new Annotation[1][1];
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getParameterAnnotations();
                will(returnValue(fakeAnnotations));
            }
        });
        expect.that(specificationMethod.getParameterAnnotations()).isTheSameInstanceAs(fakeAnnotations);
    }

    public void testReturnsNoExpectedExceptionClassForASpecThatIsAnnotated() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getMethod();
                will(returnValue(annotatedSpec));
            }
        });
        expect.that(specificationMethod.getExpectedException() == Specification.NoExpectedException.class).isTrue();
        expect.that(specificationMethod.getExpectedExceptionMessage()).isEqualTo(Specification.NO_MESSAGE);
    }

    public void testReturnsExpectedExceptionClassForASpecThatIsAnnotatedAsFailing() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getMethod();
                will(returnValue(annotatedFailingSpec));
            }
        });
        expect.that(specificationMethod.getExpectedException() == RuntimeException.class).isTrue();
        expect.that(specificationMethod.getExpectedExceptionMessage()).isEqualTo(Specification.NO_MESSAGE);
    }

    public void testReturnsNoExpectedExceptionClassForASpecThatIsNotAnnotated() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getMethod();
                will(returnValue(nonAnnotatedSpec));
            }
        });
        expect.that(specificationMethod.getExpectedException() == Specification.NoExpectedException.class).isTrue();
        expect.that(specificationMethod.getExpectedExceptionMessage()).isEqualTo(Specification.NO_MESSAGE);
    }

    public void testReturnsNoExpectedExceptionClassForANonSpecMethod() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getMethod();
                will(returnValue(notASpec));
            }
        });
        expect.that(specificationMethod.getExpectedException() == Specification.NoExpectedException.class).isTrue();
        expect.that(specificationMethod.getExpectedExceptionMessage()).isEqualTo(Specification.NO_MESSAGE);
    }

    public void testReturnsExpectedExceptionMessageClassForASpecThatIsAnnotatedAsFailing() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getMethod();
                will(returnValue(annotatedFailingSpecWithMessage));
            }
        });
        expect.that(specificationMethod.getExpectedException() == RuntimeException.class).isTrue();
        expect.that(specificationMethod.getExpectedExceptionMessage()).isEqualTo("Arrrgghh...!");
    }

    public void testReturnsPendingReasonForPendingSpecifications() {
        expect.that(new Expectations() {
            {
                atLeast(1).of(specMethod).getMethod();
                will(returnValue(pendingSpec));
            }
        });
        expect.that(specificationMethod.isPending()).isEqualTo(true);
        expect.that(specificationMethod.getPendingReason()).isEqualTo("It's pending, who needs a reason?");
    }

    private static final class ContextWithSpecificationsMarkedInDifferentWays {
        @Specification
        public void annotatedSpecificationMethod() {
        }

        @Specification(expectedException = RuntimeException.class)
        public void annotatedSpecificationMethodThatShouldFail() {
        }

        @Specification(expectedException = RuntimeException.class, withMessage = "Arrrgghh...!")
        public void annotatedSpecificationMethodThatShouldFailWithAMessage() {
        }

        @Specification(state = PENDING, reason = "It's pending, who needs a reason?")
        public void pendingSpecification() {
        }

        public void shouldBeAspecificationThatIsNotAnnotated() {
        }

        public void isNotASpecification() {
        }
    }
}
