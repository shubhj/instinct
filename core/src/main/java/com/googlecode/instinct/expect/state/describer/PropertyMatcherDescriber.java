/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.expect.state.describer;

import com.googlecode.instinct.internal.edge.org.hamcrest.MatcherDescriber;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;

public class PropertyMatcherDescriber<T> implements MatcherDescriber {

    private final CommonMatcherUtil<T> matcherUtil = new CommonMatcherUtilImpl<T>();

    private final T actual;
    private final String propertyName;
    private final Class<?> propertyType;

    public PropertyMatcherDescriber(final T actual, final String propertyName, final Class<?> propertyType) {
        checkNotNull(actual, propertyName, propertyType);
        this.actual = actual;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public final String describe() {
        final String expectedProperty = new StringBuilder().
                append(getSimpleClassName()).append(".").append(getGetter()).append(" and/or ").
                append(getSimpleClassName()).append(".").append(getSetter()).toString();
        final StringBuilder builder = new StringBuilder();
        builder.append(newLine()).
                append("Expected: ").append(expectedProperty).append(" should exist.").
            append(newLine()).append(createFiveSpaces()).append("got: ").append(expectedProperty).append(" does not exist.").
                append(newLine());
        return builder.toString();
    }

    private String createFiveSpaces() {
        return matcherUtil.space(5);
    }

    private String getSetter() {
        return matcherUtil.getSetterFor(propertyName, propertyType);
    }

    private String getGetter() {
        return matcherUtil.getGetterFor(propertyName, propertyType);
    }

    private String getSimpleClassName() {
        return matcherUtil.getSimplClassName(actual);
    }

    private String newLine() {
        return matcherUtil.newLine();
    }
}
