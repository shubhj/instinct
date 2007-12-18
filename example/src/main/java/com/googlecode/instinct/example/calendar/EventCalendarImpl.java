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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.List;

public final class EventCalendarImpl implements EventCalendar {
    private final List<Event> events = new ArrayList<Event>();

    public EventCalendarImpl(final Event... someEvents) {
        this(asList(someEvents));
    }

    private EventCalendarImpl(final Collection<Event> someEvents) {
        events.addAll(someEvents);
    }

    public Collection<Event> getEvents() {
        return copyEvents();
    }

    public EventCalendar removeEvents(final Event... someEvents) {
        final List<Event> copyOfEvents = copyEvents();
        copyOfEvents.removeAll(asList(someEvents));
        return new EventCalendarImpl(copyOfEvents);
    }

    public EventCalendar addEvents(final Event... someEvents) {
        checkNotNull((Object) someEvents);
        final List<Event> copyOfEvents = copyEvents();
        copyOfEvents.addAll(asList(someEvents));
        return new EventCalendarImpl(copyOfEvents);
    }

    public int getNumberOfEvents() {
        return events.size();
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public void clear() {
        events.clear();
    }

    private List<Event> copyEvents() {
        return new ArrayList<Event>(events);
    }
}
