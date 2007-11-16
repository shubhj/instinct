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

package com.googlecode.instinct.internal.util;

import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.marker.AnnotationAttribute.IGNORE;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.AnnotationAttribute;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;

public final class GroupSanitiserImplAtomicTest extends InstinctTestCase {
    @Subject(implementation = GroupSanitiserImpl.class) private GroupSanitiser sanitiser;

    public void testConformsToClassTraits() {
        checkClass(GroupSanitiserImpl.class, GroupSanitiser.class);
    }

    public void testConvertsEmptyStringsIntoIgnoreAttribute() {
        expect.that(sanitiser.sanitise("")).equalTo(IGNORE);
        expect.that(sanitiser.sanitise("  ")).equalTo(IGNORE);
    }

    public void testConvertsCommaSeperatedGroupsToAnnotationAttributes() {
        expect.that(sanitiser.sanitise("one")).equalTo(new AnnotationAttribute("group", new String[]{"one"}));
        expect.that(sanitiser.sanitise("one,two")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
        expect.that(sanitiser.sanitise(" one,two")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
        expect.that(sanitiser.sanitise("one,two ")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
        expect.that(sanitiser.sanitise("one, two ")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
        expect.that(sanitiser.sanitise("one, two , three")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two", "three"}));
    }

    public void testDoesNotReturnEmptyGroupsWhenLeadingComma() {
        expect.that(sanitiser.sanitise(",one")).equalTo(new AnnotationAttribute("group", new String[]{"one"}));
        expect.that(sanitiser.sanitise(",one,two")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
    }

    public void testDoesNotReturnEmptyGroupsWhenTrailingComma() {
        expect.that(sanitiser.sanitise("one,")).equalTo(new AnnotationAttribute("group", new String[]{"one"}));
        expect.that(sanitiser.sanitise("one,two,")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
    }

    public void testDoesNotReturnEmptyGroupsWhenLeadingAndTrailingComma() {
        expect.that(sanitiser.sanitise(",one,")).equalTo(new AnnotationAttribute("group", new String[]{"one"}));
        expect.that(sanitiser.sanitise(",one,two,")).equalTo(new AnnotationAttribute("group", new String[]{"one", "two"}));
    }
}