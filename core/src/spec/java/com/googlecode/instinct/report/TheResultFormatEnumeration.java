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

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class TheResultFormatEnumeration {

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
        assertTrue(ResultFormat.isEquivalentToAny("Xml"));
    }

    @Test
    public void matchingStringIsEquivalentToAny() {
        assertTrue(ResultFormat.isEquivalentToAny("BRIEF"));
        assertTrue(ResultFormat.isEquivalentToAny("QUIET"));
        assertTrue(ResultFormat.isEquivalentToAny("VERBOSE"));
        assertTrue(ResultFormat.isEquivalentToAny("XML"));
    }
}
