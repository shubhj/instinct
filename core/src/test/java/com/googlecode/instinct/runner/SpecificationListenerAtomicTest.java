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
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkInterface;

public final class SpecificationListenerAtomicTest extends InstinctTestCase {
    private SpecificationMethod specificationMethod;
    private SpecificationListener specificationListener;
    private SpecificationResult specificationResult;

    @Override
    public void setUpTestDoubles() {
        specificationMethod = mock(SpecificationMethod.class);
        specificationResult = mock(SpecificationResult.class);
    }

    @Override
    public void setUpSubject() {
        specificationListener = mock(SpecificationListener.class);
    }

    public void testConformsToClassTraits() {
        checkInterface(SpecificationListener.class);
    }

    public void testContainsPreSpecificationRunCallback() {
        expects(specificationListener).method("preSpecificationMethod");
        specificationListener.preSpecificationMethod(specificationMethod);
    }

    public void testContainsPostSpecificationRunCallback() {
        expects(specificationListener).method("postSpecificationMethod");
        specificationListener.postSpecificationMethod(specificationMethod, specificationResult);
    }
}
