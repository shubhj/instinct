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
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@SuppressWarnings({"AssignmentToNull"})
@RunWith(InstinctRunner.class)
public final class AnEmptyEventCalendar extends AbstractEventCalendarContext {
    @Subject private EventCalendar calendar = new EventCalendarImpl();

    @Override
    public EventCalendar getSubject() {
        return calendar;
    }

    @Override
    public int getDefaultSize() {
        return 0;
    }

    @Specification
    public void shouldNotHaveAnyEvents() {
        expect.that(calendar.getEvents()).isEmpty();
    }

    @Specification
    public void shouldBeAbleToAddAnEvent() {
        //the setup method in the base class has been called before each specification, which is why getEvent1() doesn't return null.
        expect.that(calendar.getEvents()).isEmpty();
        calendar = calendar.addEvents(getEvent1());
        expect.that(calendar.getEvents()).isOfSize(1);
        expect.that(calendar.getEvents()).containsItems(getEvent1());
    }

    private void tearDown() {
        // As there are 2 after specification methods; tearDown() and after() (in the base class), each will be run, but their order
        // is indeterminate.
        calendar.clear();
        calendar = null;
    }
}
