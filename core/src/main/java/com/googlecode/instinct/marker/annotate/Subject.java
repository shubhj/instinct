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

package com.googlecode.instinct.marker.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class whose behaviour is under scrutiny.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Subject {
    /**
     * For interfaces or abstract class, the implementation class of the subject. Required for auto-wiring an instance of the subject during
     * specification running.
     * @return The implementation class of the subject.
     */
    Class<?> implementation() default SubjectClassIsImplementationClass.class;

    /**
     * Whether to auto-create (auto-wire) an instance of this subject and insert the value into a context.
     * Subjects that are not auto-wired must be created some other way, such as in the field delaration or in a
     * {@linkplain BeforeSpecification before specification} method.
     * @return <code>true</code> if the subject
     */
    boolean auto() default true;

    @SuppressWarnings({"MarkerInterface"})
    interface SubjectClassIsImplementationClass {
    }
}
