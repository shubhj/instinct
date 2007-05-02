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

package com.googlecode.instinct.runner;

import static com.googlecode.instinct.expect.Mocker.expects;
import static com.googlecode.instinct.expect.Mocker.mock;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkInterface;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.runner.ContextResult;

public final class ContextListenerAtomicTest extends InstinctTestCase {
    private ContextClass contextClass;
    private ContextListener contextListener;
    private ContextResult contextResult;

    @Override
    public void setUpTestDoubles() {
        contextClass = mock(ContextClass.class);
        contextResult = mock(ContextResult.class);
    }

    @Override
    public void setUpSubject() {
        contextListener = mock(ContextListener.class);
    }

    public void testConformsToClassTraits() {
        checkInterface(ContextListener.class);
    }

    public void testContainsPreSpecificationRunCallback() {
        expects(contextListener).method("preContextRun");
        contextListener.preContextRun(contextClass);
    }

    public void testContainsPostSpecificationRunCallback() {
        expects(contextListener).method("postContextRun");
        contextListener.postContextRun(contextClass, contextResult);
    }
}
