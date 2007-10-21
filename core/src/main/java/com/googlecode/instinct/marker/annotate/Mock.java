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
 * Mocks are more advanced stubs, that not only respond to calls made during a test but are also pre-programmed with expectations which form a
 * specification of the calls they are expected to receive. Mocks will throw an exception if they receive a call they weren't expecting and are
 * checked (called verification) to ensure they received all the calls they expected. Some mocks also verify the order of calls made.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Suggest("Add componentType (for Collections) in order to fill with mocks.")
public @interface Mock {
    /**
     * Whether to auto-create (auto-wire) an instance of this mock and insert the value into a context.
     * Mocks that are not auto-wired must be created some other way, such as in the field delaration or in a
     * {@linkplain BeforeSpecification before specification} method.
     * @return <code>true</code> if the mock should be auto-wired.
     */
    boolean auto() default true;
}
