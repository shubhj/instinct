package au.id.adams.instinct.internal.runner;

import java.lang.reflect.Method;

public interface SpecificationRunner {
    <T> void runSpecification(Class<T> specificationClass, Method specificationMethod, Method[] beforeTestMethods,
                              Method[] afterTestMethods);
}
