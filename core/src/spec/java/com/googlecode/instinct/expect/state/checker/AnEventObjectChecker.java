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

package com.googlecode.instinct.expect.state.checker;

import com.googlecode.instinct.expect.state.checker.EventObjectChecker;
import com.googlecode.instinct.expect.state.checker.EventObjectCheckerImpl;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import java.util.EventObject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnEventObjectChecker {
    @Subject(auto = false) private EventObjectChecker<EventObject> checker;

    @BeforeSpecification
    public void before() {
        checker = new EventObjectCheckerImpl<EventObject>(new ASpecificationEvent(this));
    }

    @Specification
    public void shouldHandleEvents() {
        checker.isAnEventFrom(this);
        checker.isAnEventFrom(EventObject.class, this);
        checker.isAnEventFrom(ASpecificationEvent.class, this);
        checker.isNotAnEventFrom(AnotherSpecificationEvent.class, this);
    }

    private final class ASpecificationEvent extends EventObject {
        private static final long serialVersionUID = 4231685353656364481L;

        public ASpecificationEvent(final Object source) {
            super(source);
        }
    }

    private class AnotherSpecificationEvent extends EventObject {
        private static final long serialVersionUID = -5268419544552483659L;

        public AnotherSpecificationEvent(final Object source) {
            super(source);
        }
    }
}