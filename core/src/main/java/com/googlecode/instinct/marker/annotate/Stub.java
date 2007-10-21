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

package com.googlecode.instinct.marker.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.googlecode.instinct.internal.util.Suggest;

/**
 * Stubs respond to method calls made during a test by providing canned answers.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Suggest({"Add implementationClass here? Are there cases where it makes sense to declare a stub as an interface?",
        "Add componentType (for Collections) in order to fill with mocks."})
public @interface Stub {
    /**
     * Whether to auto-create (auto-wire) an instance of this stub and insert the value into a context.
     * Stubs that are not auto-wired must be created some other way, such as in the field delaration or in a
     * {@linkplain BeforeSpecification before specification} method.
     * @return <code>true</code> if the stub should be auto-wired.
     */
    boolean auto() default true;
}
