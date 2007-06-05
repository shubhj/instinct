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

import java.util.Collection;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.Mocker12.expects;
import static com.googlecode.instinct.expect.Mocker12.mock;
import static com.googlecode.instinct.expect.Mocker12.returnValue;
import static com.googlecode.instinct.expect.Mocker12.same;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.SpecificationListener;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.SubjectCreator.createSubjectWithConstructorArgs;
import static com.googlecode.instinct.test.triangulate.Triangulation.getInstance;

@SuppressWarnings({"unchecked"})
@Suggest({"Todo:", "Add a run method, pass a spec runner as a dependency, pass in other methods required for runners."})
public final class SpecificationMethodImplAtomicTest extends InstinctTestCase {
    private SpecificationMethod specificationMethod;
    private SpecificationRunner specificationRunner;
    private LifecycleMethod specMethod;
    private SpecificationResult specificationResult;
    private Collection<LifecycleMethod> beforeSpecMethods;
    private Collection<LifecycleMethod> afterSpecMethods;
    private String methodName;

    @Override
    public void setUpTestDoubles() {
        specMethod = mock(LifecycleMethod.class);
        specificationRunner = mock(SpecificationRunner.class);
        specificationResult = mock(SpecificationResult.class);
        beforeSpecMethods = mock(Collection.class);
        afterSpecMethods = mock(Collection.class);
        methodName = getInstance(String.class);
    }

    @Override
    public void setUpSubject() {
        final Object[] constructorArgs = {specMethod, beforeSpecMethods, afterSpecMethods};
        final Object[] dependencies = {specificationRunner};
        specificationMethod = createSubjectWithConstructorArgs(SpecificationMethodImpl.class, constructorArgs, dependencies);
    }

    public void testConformsToClassTraits() {
        checkClass(SpecificationMethodImpl.class, SpecificationMethod.class);
    }

    public void testPassesSpecificationListenersToSpecificationRunner() {
        for (int i = 0; i < 3; i++) {
            final SpecificationListener specificationListener = mock(SpecificationListener.class);
            expects(specificationRunner).method("addSpecificationListener").with(same(specificationListener));
            specificationMethod.addSpecificationListener(specificationListener);
        }
    }

    public void testRunsSpecificationUsingASpecificationRunner() {
        expects(specificationRunner).method("run").with(same(specificationMethod)).will(returnValue(specificationResult));
        final SpecificationResult result = specificationMethod.run();
        expect.that(result).sameInstanceAs(specificationResult);
    }

    public void testReturnsUnderlyingSpecificationMethod() {
        final LifecycleMethod returnedSpecMethod = specificationMethod.getSpecificationMethod();
        expect.that(returnedSpecMethod).sameInstanceAs(specMethod);
    }

    public void testReturnsUnderlyingBeforeSpecMethods() {
        final Collection<LifecycleMethod> returnedBeforeSpecMethods = specificationMethod.getBeforeSpecificationMethods();
        expect.that(returnedBeforeSpecMethods).sameInstanceAs(beforeSpecMethods);
    }

    public void testReturnsUnderlyingAfterSpecMethods() {
        final Collection<LifecycleMethod> returnedAfterSpecMethods = specificationMethod.getAfterSpecificationMethods();
        expect.that(returnedAfterSpecMethods).sameInstanceAs(afterSpecMethods);
    }

    public void testReturnsNameFromUnderlyingLifecycleMethod() {
        expects(specMethod).method("getName").will(returnValue(methodName));
        expect.that(specificationMethod.getName()).equalTo(methodName);
    }
}
