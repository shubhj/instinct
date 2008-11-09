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

package com.googlecode.instinct.internal.report.html;

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static com.googlecode.instinct.internal.report.html.SummaryStatus.failed;
import static com.googlecode.instinct.internal.report.html.SummaryStatus.passed;
import static com.googlecode.instinct.internal.report.html.SummaryStatus.pending;
import static com.googlecode.instinct.internal.report.html.SummaryStatus.selectMostNotable;
import com.googlecode.instinct.marker.annotate.Specification;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class TheSummaryStatusEnumeration {
    @Specification
    public void containsPassedFailedAndPending() {
        assertEquals("passed", passed.name());
        assertEquals("failed", failed.name());
        assertEquals("pending", pending.name());
    }

    @Specification
    public void considersFailedToBeMoreNotableThanPassed() {
        assertEquals(failed, selectMostNotable(passed, failed));
        assertEquals(failed, selectMostNotable(failed, passed));
    }

    @Specification
    public void considersFailedToBeMoreNotableThanPending() {
        assertEquals(failed, selectMostNotable(pending, failed));
        assertEquals(failed, selectMostNotable(failed, pending));
    }

    @Specification
    public void considersPendingToBeMoreNotableThanPassed() {
        assertEquals(pending, selectMostNotable(pending, passed));
        assertEquals(pending, selectMostNotable(passed, pending));
    }

    @Specification
    public void considersFailedToBeMostNotableOfAll() {
        assertEquals(failed, selectMostNotable(passed, failed, pending));
        assertEquals(failed, selectMostNotable(passed, pending, failed));
        assertEquals(failed, selectMostNotable(failed, passed, pending));
        assertEquals(failed, selectMostNotable(failed, pending, passed));
        assertEquals(failed, selectMostNotable(pending, passed, failed));
        assertEquals(failed, selectMostNotable(pending, failed, passed));
    }
}
