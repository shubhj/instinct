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

package com.googlecode.instinct.internal.matcher;

import com.googlecode.instinct.internal.util.Suggest;
import java.lang.reflect.Method;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

@Suggest("This is really similiar to SpecificationMatcher")
public class MethodMatcher extends BaseMatcher<Method> {
    private final String methodName;

    public MethodMatcher(final String methodName) {
        this.methodName = methodName;
    }

    public boolean matches(final Object item) {
        final Method specificationMethod = (Method) item;
        return methodName.equals(specificationMethod.getName());
    }

    public void describeTo(final Description description) {
        description.appendText("a method named ").appendValue(methodName);
    }
}
