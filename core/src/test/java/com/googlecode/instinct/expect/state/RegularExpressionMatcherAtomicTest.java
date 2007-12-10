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

package com.googlecode.instinct.expect.state;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.checker.ClassChecker.checkClass;
import static com.googlecode.instinct.internal.util.Reflector.getMethod;
import java.lang.reflect.Method;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeMatcher;

public final class RegularExpressionMatcherAtomicTest extends InstinctTestCase {
    public void testConformsToClassTraits() {
        checkClass(RegularExpressionMatcher.class, TypeSafeMatcher.class);
    }

    public void testPerformsMatchesOnStringAgainstAGivenRegularExpression() {
        expectMatches(".*", "");
        expectMatches("[a-z]*", "aaaaaa");
        expectDoesNotMatch("[a-z]+", "123");
        expectDoesNotMatch("[0-9]+", "AAAA");
    }

    public void testDescribesErrorsByAppendingTheExpectedRegularExpression() {
        checkDescribesErrorsByAppendingTheExpectedRegularExpression(".*");
        checkDescribesErrorsByAppendingTheExpectedRegularExpression("[a-z]+");
    }

    public void testContainsAStaticFactoryMethodAnnotatedWithHamcrestFactoryAnnotation() {
        final Method matchesRegexMethod = getMethod(RegularExpressionMatcher.class, "matchesRegex", String.class);
        final Factory factoryAnnotation = matchesRegexMethod.getAnnotation(Factory.class);
        expect.that(factoryAnnotation).isNotNull();
    }

    private void expectMatches(final String regularExpression, final String stringToMatch) {
        match(regularExpression, stringToMatch, true);
    }

    private void expectDoesNotMatch(final String regularExpression, final String stringToMatch) {
        match(regularExpression, stringToMatch, false);
    }

    private void match(final String regularExpression, final String stringToMatch, final boolean expectMatch) {
        final Matcher<String> matcher = RegularExpressionMatcher.matchesRegex(regularExpression);
        final Boolean matches = matcher.matches(stringToMatch);
        expect.that(matches).isEqualTo(expectMatch);
    }

    private void checkDescribesErrorsByAppendingTheExpectedRegularExpression(final String regularExpression) {
        final Matcher<String> matcher = RegularExpressionMatcher.matchesRegex(regularExpression);
        final Description description = new StringDescription();
        matcher.describeTo(description);
        expect.that(description.toString()).containsString("a string matching regular expression /" + regularExpression + '/');
    }
}
