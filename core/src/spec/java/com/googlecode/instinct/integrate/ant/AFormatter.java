/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.test.actor.SubjectCreatorImpl;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AFormatter {
    @Subject Formatter formatter;

    @Before
    public void setUp() throws NoSuchFieldException {
        formatter = (Formatter) new SubjectCreatorImpl().create(getClass().getDeclaredField("formatter"));
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void wontAcceptANullType() {
        formatter.setType(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void wontAcceptAnEmptyType() {
        formatter.setType("");
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void wontAcceptAnExclusivelyWhitespaceType() {
        formatter.setType("\t\n ");
    }

    @Specification(expectedException = UnsupportedOperationException.class)
    public void wontAcceptAnUnrecognisedType() {
        formatter.setType("vociferous");
    }

    @Specification
    public void acceptsRecognisedTypes() {
        formatter.setType("brief");
        formatter.setType("quiet");
        formatter.setType("verbose");
    }

    @Specification
    public void acceptsRecognisedTypesDespiteChangedCase() {
        formatter.setType("Brief");
        formatter.setType("Quiet");
        formatter.setType("Verbose");
    }
}
