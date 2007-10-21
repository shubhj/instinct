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

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.reflect.TestSubjectCreator.createSubject;
import org.jmock.Expectations;

public final class ActorAutoWirerImplAtomicTest extends InstinctTestCase {
    @Subject(auto = false) private ActorAutoWirer actorAutoWirer;
    @Mock private MarkedFieldLocator markedFieldLocator;

    @Override
    public void setUpSubject() {
        actorAutoWirer = createSubject(ActorAutoWirerImpl.class, markedFieldLocator);
    }

    @Suggest("Come back here after fixing MarkedFieldLocator")
    public void testAutoWiresDummiesIntoClasses() {
        expect.that(new Expectations() {
            {
//                markedFieldLocator.locateAll(SomeClassWithMarkedFields.class, );
            }
        });
    }

    private static final class SomeClassWithMarkedFields {
        @Subject private CharSequence aSubject;
        @Mock private CharSequence aMock;
        @Stub private CharSequence aStub;
        @Dummy private CharSequence aDummy;
    }
}
