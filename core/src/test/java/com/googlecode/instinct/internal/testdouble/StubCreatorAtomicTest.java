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

package com.googlecode.instinct.internal.testdouble;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import org.jmock.Expectations;

// Note. Cannot use auto-wired stubs here as we're testing the stub creator.
public final class StubCreatorAtomicTest extends InstinctTestCase {
    @Stub(auto = false) private String theStub = "A string";
    @Subject(auto = false) private SpecificationDoubleCreator stubCreator;
    @Mock private InstanceProvider instanceProvider;

    @Override
    public void setUpSubject() {
        stubCreator = createSubject(StubCreator.class, instanceProvider);
    }

    public void testConformsToClassTraits() {
        checkClass(StubCreator.class, SpecificationDoubleCreator.class);
    }

    public void testDelegatesAllCallsToInstanceProvider() {
        expect.that(new Expectations() {
            {
                one(instanceProvider).newInstance(String.class);
                will(returnValue(theStub));
            }
        });
        final String returnedStub = stubCreator.createDouble(String.class, "role name is ignored");
        expect.that(returnedStub).sameInstanceAs(theStub);
    }
}
