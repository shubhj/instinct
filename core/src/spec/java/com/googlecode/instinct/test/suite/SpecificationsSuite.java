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

package com.googlecode.instinct.test.suite;

import com.googlecode.instinct.actor.ADummyAutoWireDeterminatorWithANamedFieldNotToAutoWire;
import com.googlecode.instinct.actor.ADummyAutoWireDeterminatorWithANamedFieldToAutoWire;
import com.googlecode.instinct.actor.ADummyAutoWireDeterminatorWithAnAnnoatedFieldToAutoWire;
import com.googlecode.instinct.actor.ADummyAutoWireDeterminatorWithAnAnnotatedFieldNotToAutoWire;
import com.googlecode.instinct.actor.ADummyAutoWireDeterminatorWithNoMarkedFields;
import com.googlecode.instinct.actor.AMockAutoWireDeterminatorWithANamedFieldNotToAutoWire;
import com.googlecode.instinct.actor.AMockAutoWireDeterminatorWithANamedFieldToAutoWire;
import com.googlecode.instinct.actor.AMockAutoWireDeterminatorWithAnAnnoatedFieldToAutoWire;
import com.googlecode.instinct.actor.AMockAutoWireDeterminatorWithAnAnnotatedFieldNotToAutoWire;
import com.googlecode.instinct.actor.AMockAutoWireDeterminatorWithNoMarkedFields;
import com.googlecode.instinct.actor.AStubAutoWireDeterminatorWithANamedFieldNotToAutoWire;
import com.googlecode.instinct.actor.AStubAutoWireDeterminatorWithANamedFieldToAutoWire;
import com.googlecode.instinct.actor.AStubAutoWireDeterminatorWithAnAnnoatedFieldToAutoWire;
import com.googlecode.instinct.actor.AStubAutoWireDeterminatorWithAnAnnotatedFieldNotToAutoWire;
import com.googlecode.instinct.actor.AStubAutoWireDeterminatorWithNoMarkedFields;
import com.googlecode.instinct.api.AnObjectCheckerContext;
import com.googlecode.instinct.api.AnObjectCheckerFailure;
import com.googlecode.instinct.api.CommonAPIContext;
import com.googlecode.instinct.defect.defect23.AFixedDefect23;
import com.googlecode.instinct.defect.defect3.AFixedDefect3;
import com.googlecode.instinct.defect.defect8.AFixedDefect8WithANamingConventionLocator;
import com.googlecode.instinct.defect.defect8.AFixedDefect8WithAnAnnotationMethodLocator;
import com.googlecode.instinct.expect.state.matcher.AFjItemInListMatcher;
import com.googlecode.instinct.integrate.junit3.ContextTestSuite;
import com.googlecode.instinct.internal.core.APendingSpecificationMethodWithAReason;
import com.googlecode.instinct.internal.core.APendingSpecificationMethodWithoutAReason;
import com.googlecode.instinct.internal.core.ASpecificationMethodBuilder;
import com.googlecode.instinct.internal.core.ASpecificationThatExpectsAnExceptionButOneIsNotThrown;
import com.googlecode.instinct.internal.core.ASpecificationThatThrowsAnExpectedException;
import com.googlecode.instinct.internal.core.AnExectingExceptionSpecificationMethod;
import com.googlecode.instinct.internal.edge.org.hamcrest.AStringFactoryImplContext;
import com.googlecode.instinct.internal.locate.field.ANamedFieldLocatorWithFieldsToFind;
import com.googlecode.instinct.internal.locate.field.ANamedFieldLocatorWithNoFieldsToFind;
import com.googlecode.instinct.internal.locate.method.ASuperClassTraversingMethodLocator;
import com.googlecode.instinct.internal.locate.method.AnAnnotatedMethodLocatorContext;
import com.googlecode.instinct.internal.util.AMethodEqualityUtility;
import com.googlecode.instinct.internal.util.ExceptionSanitiserWithKnownException;
import com.googlecode.instinct.internal.util.ExceptionSanitiserWithUnknownException;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.Test;
import junit.framework.TestSuite;

public final class SpecificationsSuite {
    private SpecificationsSuite() {
        throw new UnsupportedOperationException();
    }

    @Suggest("Turn this into a JUnit 4 suite.")
    public static Test suite() {
        final TestSuite suite = new TestSuite("Specifications");
        for (final Class<?> cls : getContexts()) {
            suite.addTest(new ContextTestSuite(cls));
        }
        return suite;
    }

    @Suggest("We should be able to automatically find these. Maybe add a group to all 'testdata' specs so they don't run.")
    private static Class<?>[] getContexts() {
        return new Class<?>[]{ExceptionSanitiserWithKnownException.class, ExceptionSanitiserWithUnknownException.class,
                AnAnnotatedMethodLocatorContext.class, AFixedDefect8WithAnAnnotationMethodLocator.class,
                AFixedDefect8WithANamingConventionLocator.class, AFixedDefect23.class, AFixedDefect3.class, CommonAPIContext.class,
                AnObjectCheckerContext.class, AnObjectCheckerFailure.class, AStringFactoryImplContext.class,
                ANamedFieldLocatorWithNoFieldsToFind.class, ANamedFieldLocatorWithFieldsToFind.class,
                AMockAutoWireDeterminatorWithAnAnnoatedFieldToAutoWire.class, AMockAutoWireDeterminatorWithAnAnnotatedFieldNotToAutoWire.class,
                AMockAutoWireDeterminatorWithANamedFieldToAutoWire.class, AMockAutoWireDeterminatorWithANamedFieldNotToAutoWire.class,
                AMockAutoWireDeterminatorWithNoMarkedFields.class, AStubAutoWireDeterminatorWithAnAnnoatedFieldToAutoWire.class,
                AStubAutoWireDeterminatorWithAnAnnotatedFieldNotToAutoWire.class, AStubAutoWireDeterminatorWithANamedFieldToAutoWire.class,
                AStubAutoWireDeterminatorWithANamedFieldNotToAutoWire.class, AStubAutoWireDeterminatorWithNoMarkedFields.class,
                ADummyAutoWireDeterminatorWithAnAnnoatedFieldToAutoWire.class, ADummyAutoWireDeterminatorWithAnAnnotatedFieldNotToAutoWire.class,
                ADummyAutoWireDeterminatorWithANamedFieldToAutoWire.class, ADummyAutoWireDeterminatorWithANamedFieldNotToAutoWire.class,
                ADummyAutoWireDeterminatorWithNoMarkedFields.class, APendingSpecificationMethodWithoutAReason.class,
                APendingSpecificationMethodWithAReason.class, ASpecificationThatThrowsAnExpectedException.class,
                AnExectingExceptionSpecificationMethod.class, ASpecificationThatExpectsAnExceptionButOneIsNotThrown.class,
                ASpecificationMethodBuilder.class, AFjItemInListMatcher.class, AMethodEqualityUtility.class,
                ASuperClassTraversingMethodLocator.class};
    }
}
