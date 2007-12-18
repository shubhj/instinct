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

package com.googlecode.instinct.internal.trait.param;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.InstanceProvider;

public final class MehodNullParameterTestCheckerImpl implements MehodNullParameterTestChecker {
    private final ParameterCheckerTestUtil parameterUtil;

    public MehodNullParameterTestCheckerImpl(final InstanceProvider instanceProvider) {
        checkNotNull(instanceProvider);
        parameterUtil = new ParameterCheckerTestUtilImpl(instanceProvider);
    }

    public void checkPublicMethodsRejectNull(final Object instance) {
        checkNotNull(instance);
        parameterUtil.checkMethodsRejectsNull(instance);
    }
}
