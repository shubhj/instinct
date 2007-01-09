package com.googlecode.instinct.core.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.googlecode.instinct.internal.util.Suggest;

/**
 * A method that is run before every specification is run.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Suggest("Consider using JUnit style before, before class, after & after class")
public @interface BeforeSpecification {
}
