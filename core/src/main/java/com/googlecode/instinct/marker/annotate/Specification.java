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
 * A specification is method in which the behaviour of a piece of code is specified.
 * Specifications are executable examples that guide the design process and provide both documentation and tests. Specifications (specs) are analogous
 * to test methods in XUnit frameworks.
 */
@SuppressWarnings({"PublicStaticArrayField"})
@Documented
@Retention(RUNTIME)
@Target({METHOD})
public @interface Specification {
    String NO_MESSAGE = "Specification is not expected to throw an exception.";
    String NO_REASON = "";
    String ALL_GROUPS = "ALL";

    /**
     * The state of a specification. Pending specifications are not run by Instinct (but are still reported).
     * @return The state of the specification.
     */
    SpecificationState state() default SpecificationState.COMPLETE;

    /**
     * The reason the specification has been given its @link{state()}.
     * @return The reason the specification has been given its state.
     */
    String reason() default NO_REASON;

    /**
     * The group(s) the specification belongs to.
     * Groups can be used to run different sets of specifications at different times, for example fast specifications and slow specifications.
     * @return The group(s) the specification belongs to.
     */
    String[] groups() default "ALL";

    /**
     * The exception the specification is expected to throw, that is, successfully executing the specification will yield the exception.
     * If this is not specified, the specification is assumed to succeed and any errors will be reported as failures.
     * @return The exception the specification is expected to throw.
     */
    Class<? extends Throwable> expectedException() default NoExpectedException.class;

    /**
     * The message within the exception that this specification is expected to throw.
     * Specifying this field is optional, if it is not supplied the exception message will not be checked.
     * This field must be used together with @link{expectedException()} or not at all.
     * @return The message of the exception that this specification is expected to throw.
     */
    String withMessage() default NO_MESSAGE;

    enum SpecificationState {
        /**
         * The specification and/or the behaviour it is specifying is currently under development.
         */
        IN_PROGRESS,

        /**
         * The specification and/or the behaviour is not complete. Pending specifications are not run by Instinct, however they are reported in
         * specification results.
         */
        PENDING,

        /**
         * The specification and/or the behaviour it is specifying is complete. This is the default state of a specification.
         */
        COMPLETE
    }

    @SuppressWarnings({"NonExceptionNameEndsWithException"})
    class NoExpectedException extends Throwable {
        private static final long serialVersionUID = -8070871793364595469L;
    }
}
