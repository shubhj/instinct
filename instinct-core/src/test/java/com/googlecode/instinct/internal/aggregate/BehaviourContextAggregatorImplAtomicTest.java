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

package com.googlecode.instinct.internal.aggregate;

import com.googlecode.instinct.internal.aggregate.locate.ClassLocator;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.mock.Mocker.anything;
import static com.googlecode.instinct.mock.Mocker.expects;
import static com.googlecode.instinct.mock.Mocker.mock;
import static com.googlecode.instinct.mock.Mocker.once;
import static com.googlecode.instinct.mock.Mocker.returnValue;
import static com.googlecode.instinct.mock.Mocker.verify;
import com.googlecode.instinct.test.InstinctTestCase;

public final class BehaviourContextAggregatorImplAtomicTest extends InstinctTestCase {
    public void testGetContextNames() {
        // create test objects
        final JavaClassName[] classNames = {mock(JavaClassName.class)};
        final ClassLocator classLocator = mock(ClassLocator.class);
        final BehaviourContextAggregator aggregator = new BehaviourContextAggregatorImpl(BehaviourContextAggregatorImplAtomicTest.class,
                classLocator);

        // setup expectations
        expects(classLocator, once()).method("locate").with(anything(), anything()).will(returnValue(classNames));

        //expects(classLocator).method("locate", once()).with(anything(), anything()).will(returnValue(classNames));

        // do actual work
        final JavaClassName[] names = aggregator.getContextNames();

        // run checks
        assertSame(classNames, names);
        verify();
    }
}
