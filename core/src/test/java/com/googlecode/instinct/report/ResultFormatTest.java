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

package com.googlecode.instinct.report;

import com.googlecode.instinct.internal.report.BriefResultMessageBuilder;
import com.googlecode.instinct.internal.report.QuietResultMessageBuilder;
import com.googlecode.instinct.internal.report.VerboseResultMessageBuilder;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.report.ResultFormat.QUIET;
import static com.googlecode.instinct.report.ResultFormat.VERBOSE;
import java.util.EnumSet;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public final class ResultFormatTest {

    @Test
    public void providesAppropriateBriefMessageBuilder() {
        final ResultMessageBuilder builder = BRIEF.getMessageBuilder();
        assertNotNull(builder);
        assertEquals(BriefResultMessageBuilder.class, builder.getClass());
    }

    @Test
    public void providesAppropriateQuietMessageBuilder() {
        final ResultMessageBuilder builder = QUIET.getMessageBuilder();
        assertNotNull(builder);
        assertEquals(QuietResultMessageBuilder.class, builder.getClass());
    }

    @Test
    public void providesAppropriateVerboseMessageBuilder() {
        final ResultMessageBuilder builder = VERBOSE.getMessageBuilder();
        assertNotNull(builder);
        assertEquals(VerboseResultMessageBuilder.class, builder.getClass());
    }

    @Test
    public void providesNewMessageBuilderInstancesPerRequest() {
        for (final ResultFormat format : EnumSet.allOf(ResultFormat.class)) {
            assertFalse("ResultFormat " + format + " provided the same message builder instance twice",
                    format.getMessageBuilder() == format.getMessageBuilder());
        }
    }

    @Test
    public void nullIsNotEquivalentToAny() {
        assertFalse(ResultFormat.isEquivalentToAny(null));
    }

    @Test
    public void nonmatchingStringIsNotEquivalentToAny() {
        assertFalse(ResultFormat.isEquivalentToAny("vociferous"));
    }

    @Test
    public void matchingStringIsEquivalentToAnyDespiteWrongCase() {
        assertTrue(ResultFormat.isEquivalentToAny("Brief"));
        assertTrue(ResultFormat.isEquivalentToAny("Quiet"));
        assertTrue(ResultFormat.isEquivalentToAny("Verbose"));
    }

    @Test
    public void matchingStringIsEquivalentToAny() {
        assertTrue(ResultFormat.isEquivalentToAny("BRIEF"));
        assertTrue(ResultFormat.isEquivalentToAny("QUIET"));
        assertTrue(ResultFormat.isEquivalentToAny("VERBOSE"));
    }
}
