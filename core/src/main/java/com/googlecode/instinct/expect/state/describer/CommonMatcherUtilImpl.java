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

import com.googlecode.instinct.internal.edge.org.hamcrest.StringFactory;
import com.googlecode.instinct.internal.edge.org.hamcrest.StringFactoryImpl;
import com.googlecode.instinct.internal.util.ClassUtil;
import com.googlecode.instinct.internal.util.ClassUtilImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public final class CommonMatcherUtilImpl<T> implements CommonMatcherUtil<T> {
    private final ClassUtil classUtil = new ClassUtilImpl();
    private final StringFactory stringFactory = new StringFactoryImpl();

    public String describeValue(final T value) {
        final Description description = new StringDescription();
        description.appendValue(value);
        return description.toString();
    }

    public String describeExpectation(final Matcher<T> matcher) {
        final Description description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }

    public String getGetterFor(final String propertyName, final Class<?> propertyType) {
        checkNotNull(propertyName);
        String prefix = "get";
        if (classUtil.isBooleanType(propertyType)) {
            prefix = "is";
        }
        return prefix + classUtil.capitalizeFirstCharacter(propertyName) + "()";
    }

    public String getSetterFor(final String propertyName, final Class<?> propertyType) {
        checkNotNull(propertyName);
        return "set" + classUtil.capitalizeFirstCharacter(propertyName) + "(" + propertyType.getSimpleName() + ")";
    }

    public String getSimplClassName(final Object instance) {
        return instance.getClass().getSimpleName();
    }

    public String newLine() {
        return stringFactory.newLine();
    }

    public String space(final int spaces) {
        return stringFactory.space(5);
    }
}
