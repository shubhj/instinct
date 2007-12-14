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

package com.googlecode.instinct.example.calendar;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.AfterSpecification;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import java.util.ArrayList;

@SuppressWarnings({"AssignmentToNull", "NullArgumentToVariableArgMethod"})
public abstract class AbstractEventCalendarContext {
    private Event event1;
    private Event event2;

    @BeforeSpecification
    public void before() {
        event1 = createEvent("Event1");
        event2 = createEvent("Event2");
    }

    @AfterSpecification
    public void after() {
        event1 = null;
        event2 = null;
    }

    protected abstract EventCalendar getSubject();

    protected abstract int getDefaultSize();

    protected final Event getEvent1() {
        return event1;
    }

    protected final Event getEvent2() {
        return event2;
    }

    protected final Event createEvent(final String eventName) {
        return new EventImpl(eventName, new EventDurationImpl(), new ArrayList<Attendee>());
    }

    @Specification
    public void shouldHaveTheCorrectInitialSize() {
        // Will be run for each subclass with values from the getNumberOfEvents() and getDefaultSize() methods.
        expect.that(getSubject().getNumberOfEvents()).isEqualTo(getDefaultSize());
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArumentExceptionIfAnAddedEventIsNull() {
        // Will be run for each subclass.
        getSubject().addEvents((Event[]) null);
    }
}
