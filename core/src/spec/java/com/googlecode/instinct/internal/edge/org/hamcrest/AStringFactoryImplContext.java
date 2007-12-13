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

package com.googlecode.instinct.internal.edge.org.hamcrest;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context
public class AStringFactoryImplContext {

    @Subject StringFactory characterFactory = new StringFactoryImpl();
    private static final String NL = System.getProperty("line.separator");

    @Specification
    public void shouldCreatedFormattingStrings() {
        final StringBuilder builder = new StringBuilder();
                builder.append("Expected: ").append(quoted("true"))
                .append(newLine()).
                append("Received:").append(createTenSpaces()).append(quoted("false"));
        expect.that("Expected: \"true\"" + NL + "Received:          \"false\"").isEqualTo(builder.toString());
    }

    private String createTenSpaces() {
        return characterFactory.space(10);
    }

    private String quoted(final String value) {
        return characterFactory.quoted(value);
    }

    private String newLine() {
        return characterFactory.newLine();
    }
}