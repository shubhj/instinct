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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.core.BehaviourContextConfigurationException;
import static com.googlecode.instinct.test.AssertTestChecker.assertThrows;
import com.googlecode.instinct.test.InstinctTestCase;

public final class BehaviourContextRunnerSlowTest extends InstinctTestCase {
    private BehaviourContextRunner runner;

    public void testRunWithSuccess() {
        runContext(ContextContainerWithSetUpAndTearDown.class);
        runContext(ContextContainerWithSetUpAndTearDown.AnEmbeddedPublicContext.class);
        runContext(ContextContainerWithConstructors.APublicConstructor.class);
    }

    public void testInvalidConstructorsThrowConfigException() {
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.AConstructorWithParameters.class);
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.APrivateConstructor.class);
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.APackageLocalConstructor.class);
        checkInvalidConstructorsThrowConfigException(ContextContainerWithConstructors.AProtectedConstructor.class);
    }

    private <T> void checkInvalidConstructorsThrowConfigException(final Class<T> cls) {
        assertThrows(BehaviourContextConfigurationException.class, new Runnable() {
            public void run() {
                runContext(cls);
            }
        });
    }

    private <T> void runContext(final Class<T> cls) {
        runner.run(cls);
    }

    @Override
    public void setUpSubject() {
        runner = new BehaviourContextRunnerImpl();
    }
}
