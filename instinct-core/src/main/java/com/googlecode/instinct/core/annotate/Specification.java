package com.googlecode.instinct.core.annotate;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * A specification of behaviour, i.e. what a piece of code must do.
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD})
public @interface Specification {
}
