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

package com.googlecode.instinct.example.calendar;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnEventCalendarWithAnEvent extends AbstractEventCalendarContext {
    @Subject private EventCalendar calendar = new EventCalendarImpl();

    @Override
    @BeforeSpecification
    public void before() {
        try {
            calendar = calendar.addEvents(getEvent1());
        } catch (IllegalArgumentException e) {
            // Shows that we have successfully overridden the setup method of the base class. (which will not be called).
            super.before();
            calendar = calendar.addEvents(getEvent1());
        }
    }

    @Override
    public EventCalendar getSubject() {
        return calendar;
    }

    @Override
    public int getDefaultSize() {
        return 1;
    }

    @Specification
    public void shouldReturnTheAddedEvent() {
        expect.that(calendar.getEvents()).isOfSize(1);
        expect.that(calendar.getEvents().contains(getEvent1()));
    }

    @Specification
    public void shouldBeAbleToRemoveTheAddedEvent() {
        expect.that(calendar.getEvents()).isOfSize(1);
        calendar = calendar.removeEvents(getEvent1());
        expect.that(calendar.getEvents()).isEmpty();
    }

    @Specification
    public void shouldBeAbleToAddAnotherEvent() {
        expect.that(calendar.getEvents()).isOfSize(1);
        calendar = calendar.addEvents(getEvent2());
        expect.that(calendar.getEvents()).isOfSize(2);
        expect.that(calendar.getEvents()).containsItems(getEvent1(), getEvent2());
    }
}
