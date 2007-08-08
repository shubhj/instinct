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

package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.runner.SpecificationListener;
import java.util.Collection;
import junit.framework.TestSuite;

@Fix("Make JUnit integration work again. Add to example ant build")
public final class ContextTestSuite extends TestSuite implements SpecificationListener {
    private final ContextClass contextClass;

    public ContextTestSuite(final ContextClass contextClass) {
        checkNotNull(contextClass);
        this.contextClass = contextClass;
        // Do we really want to pass an unconstructed "this" to another class?
        contextClass.addSpecificationListener(this);
        addToSuite(contextClass.buildSpecificationMethods());
    }

    @Override
    public String getName() {
        return contextClass.getName();
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        addTest(new SpecificationTestCase(specificationMethod));
    }

    public void postSpecificationMethod(final SpecificationMethod specificationMethod, final SpecificationResult specificationResult) {
        checkNotNull(specificationMethod, specificationResult);
        // ignored
    }

    private void addToSuite(final Collection<SpecificationMethod> specificationMethods) {
        for (final SpecificationMethod specificationMethod : specificationMethods) {
            addTest(new SpecificationTestCase(specificationMethod));
        }
    }
}
