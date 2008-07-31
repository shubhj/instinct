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

import com.googlecode.instinct.marker.naming.AfterSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.BeforeSpecificationNamingConvention;
import com.googlecode.instinct.marker.naming.NamingConvention;
import com.googlecode.instinct.marker.naming.SpecificationNamingConvention;
import com.googlecode.instinct.runner.SpecificationLifecycle;
import com.googlecode.instinct.runner.SpecificationLifecycleImpl;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * A context in which a certain behaviour is valid. Contexts can be used to group specifications together and set up state in which a number of
 * specifications are valid.
 */
@Documented
@Inherited
@Retention(RUNTIME)
@Target({TYPE})
public @interface Context {
    /**
     * The group(s) the specifications in this context belong to. Setting this attribute will cause all specifications in the context to be marked as
     * belonging to the specified group. In this way using this attribute is a shorthand method of marking every individual specification.
     * @return The group(s) the specifications in this context belong to.
     * @see Specification#groups()
     */
    String[] groups() default "ALL";

    /**
     * The lifecycle to use to run each of the specifications in this context. Changing this will cause each specification to be run using this
     * lifecycle instead of the default one.
     * @return The lifecycle to use to run each of the specifications in this context.
     */
    Class<? extends SpecificationLifecycle> lifecycle() default SpecificationLifecycleImpl.class;

    /**
     * The naming convention to use to find methods to be run before every specification. Changing this will cause Instinct's method locators to use
     * this naming convention instead of the default one.
     * @return The naming convention to use to find methods to be run before every specification.
     */
    Class<? extends NamingConvention> beforeSpecificationNamingConvention() default BeforeSpecificationNamingConvention.class;

    /**
     * The annotation to use to find methods to be run before every specification. Changing this will cause Instinct's method locators to use this
     * annotation instead of the default one.
     * @return The annotation to use to find methods to be run before every specification.
     */
    Class<? extends Annotation> beforeSpecificationAnnotation() default BeforeSpecification.class;

    /**
     * The naming convention to use to find methods to be run after every specification. Changing this will cause Instinct's method locators to use
     * this naming convention instead of the default one.
     * @return The naming convention to use to find methods to be run after every specification.
     */
    Class<? extends NamingConvention> afterSpecificationNamingConvention() default AfterSpecificationNamingConvention.class;

    /**
     * The annotation to use to find methods to be run after every specification. Changing this will cause Instinct's method locators to use this
     * annotation instead of the default one.
     * @return The annotation to use to find methods to be run after every specification.
     */
    Class<? extends Annotation> afterSpecificationAnnotation() default AfterSpecification.class;

    /**
     * The naming convention to use to find specification methods. Changing this will cause Instinct's method locators to use this naming convention
     * instead of the default one.
     * @return The naming convention to use to find specification methods.
     */
    Class<? extends NamingConvention> specificationNamingConvention() default SpecificationNamingConvention.class;

    /**
     * The annotation to use to find specification methods. Changing this will cause Instinct's method locators to use this annotation instead of the
     * default one.
     * @return The annotation to use to find specification methods.
     */
    Class<? extends Annotation> specificationAnnotation() default Specification.class;
}
