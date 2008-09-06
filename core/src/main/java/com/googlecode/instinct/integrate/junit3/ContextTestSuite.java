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
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;
import com.googlecode.instinct.runner.SpecificationListener;
import fj.Effect;
import fj.data.List;
import junit.framework.TestSuite;

@SuppressWarnings({"ThisEscapedInObjectConstruction"})
public final class ContextTestSuite extends TestSuite implements SpecificationListener {
    private final ObjectFactory objectFactory = new ObjectFactoryImpl();
    private final ContextClass contextClass;

    public <T> ContextTestSuite(final Class<T> contextType) {
        checkNotNull(contextType);
        contextClass = objectFactory.create(ContextClassImpl.class, contextType);
        contextClass.addSpecificationListener(this);
        addSpecificationsToSuite(contextClass.getSpecificationMethods());
    }

    @Override
    public String getName() {
        return contextClass.getName();
    }

    public void preSpecificationMethod(final SpecificationMethod specificationMethod) {
        checkNotNull(specificationMethod);
        addTest(new SpecificationTestCase(specificationMethod));
    }

    public void postSpecificationMethod(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        // ignored
    }

    private void addSpecificationsToSuite(final List<SpecificationMethod> specificationMethods) {
        specificationMethods.foreach(new Effect<SpecificationMethod>() {
            public void e(final SpecificationMethod a) {
                addTest(new SpecificationTestCase(a));
            }
        });
    }
}
