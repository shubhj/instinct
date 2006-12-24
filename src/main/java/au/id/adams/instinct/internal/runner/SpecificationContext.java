package au.id.adams.instinct.internal.runner;

import java.lang.reflect.Method;

interface SpecificationContext {
    Method[] getBeforeSpecificationMethods();
    Method[] getAfterSpecificationMethods();
    Method getSpecificationMethod();
}
