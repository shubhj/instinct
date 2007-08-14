/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.integrate.junit4;

import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.util.ParamChecker;
import java.util.Collection;
import org.junit.runner.notification.RunNotifier;

public final class SpecificationRunnerImpl implements SpecificationRunner {
    public SpecificationRunnerImpl(final RunNotifier notifier) {
        ParamChecker.checkNotNull(notifier);
    }

    public void run(final Collection<SpecificationMethod> specificationMethods) {
        ParamChecker.checkNotNull(specificationMethods);
    }
}