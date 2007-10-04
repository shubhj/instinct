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
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * A method in which the behaviour of a piece of code is specified.
 * Specifications are executable examples that guide the design process and provide both documentation and tests. Specifications (specs) can be
 * thought of as test methods in XUnit frameworks.
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD})
public @interface Specification {
    /**
     * The state of a specification. Pending specifications are not run by Instinct (but are still reported).
     *
     * @return The state of the specification.
     */
    SpecificationState state() default SpecificationState.COMPLETE;

    /**
     * The group the specification belongs to.
     * Groups can be used to run different sets of specifications at different times, for example slow specifications and slow specifications.
     * @return The group the specification belongs to.
     */
    String[] groups() default "None";

    enum SpecificationState {
        PENDING, COMPLETE
    }
}
