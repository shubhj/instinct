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

import java.util.ArrayList;
import java.util.Collection;

public final class EventImpl implements Event {

    private final String eventName;
    private final EventDuration eventDuration;
    private final Collection<Attendee> eventAttendees;

    public EventImpl(final String eventName, final EventDuration eventDuration, final Collection<Attendee> attendees) {
        this.eventName = eventName;
        this.eventDuration = eventDuration;
        eventAttendees = copyAttendees(attendees);
    }

    public String getEventName() {
        return eventName;
    }

    public EventDuration getEventDuration() {
        return eventDuration;
    }

    public Collection<Attendee> getEventAttendees() {
        return copyAttendees();
    }

    private Collection<Attendee> copyAttendees(final Collection<Attendee> attendees) {
        return new ArrayList<Attendee>(attendees);
    }

    private Collection<Attendee> copyAttendees() {
        return copyAttendees(eventAttendees);
    }
}
