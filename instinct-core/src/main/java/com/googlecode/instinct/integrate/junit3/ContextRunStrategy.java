package com.googlecode.instinct.integrate.junit3;

import com.googlecode.instinct.internal.runner.ContextResult;

public interface ContextRunStrategy {
    ContextResult onContext(ContextClass context);
}
