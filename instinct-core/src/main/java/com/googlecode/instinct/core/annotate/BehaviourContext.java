package com.googlecode.instinct.core.annotate;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RUNTIME)
@Target({TYPE})
public @interface BehaviourContext {
}
