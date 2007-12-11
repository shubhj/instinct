/*
 * Copyright 2006-2007 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.api;

import com.googlecode.instinct.expect.state.checker.EventObjectChecker;
import com.googlecode.instinct.expect.state.checker.EventObjectCheckerImpl;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.EventObject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AnEventObjectChecker {

    @Specification
    public void shouldHandleEvents() {
        final EventObject eventObject = new ASpecificationEvent(this);
        final EventObjectChecker<EventObject> objectChecker = new EventObjectCheckerImpl<EventObject>(eventObject);

        objectChecker.isAnEventFrom(this);
        objectChecker.isAnEventFrom(EventObject.class, this);
        objectChecker.isAnEventFrom(ASpecificationEvent.class, this);
        objectChecker.isNotAnEventFrom(AnotherSpecificationEvent.class, this);
    }

    private class ASpecificationEvent extends EventObject {

        public ASpecificationEvent(final Object source) {
            super(source);
        }
    }

    private class AnotherSpecificationEvent extends EventObject {

        public AnotherSpecificationEvent(final Object source) {
            super(source);
        }
    }
}