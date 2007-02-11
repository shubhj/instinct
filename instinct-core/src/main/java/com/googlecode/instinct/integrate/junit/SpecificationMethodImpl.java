package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.Method;

public final class SpecificationMethodImpl implements SpecificationMethod {
    private final Method specificationMethod;

    public SpecificationMethodImpl(final Method specificationMethod) {
        this.specificationMethod = specificationMethod;
    }

    public String getName() {
        return specificationMethod.getName();
    }
}
