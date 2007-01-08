package com.googlecode.instinct.internal.runner;

import java.lang.reflect.Method;

interface SpecificationContext {
    Class<?> getBehaviourContextClass();
    Method[] getBeforeSpecificationMethods();
    Method[] getAfterSpecificationMethods();
    Method getSpecificationMethod();
}
