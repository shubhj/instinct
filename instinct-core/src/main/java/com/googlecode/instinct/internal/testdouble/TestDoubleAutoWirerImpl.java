package com.googlecode.instinct.internal.testdouble;

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

public final class TestDoubleAutoWirerImpl implements TestDoubleAutoWirer {
    @Suggest("Come back here after creating an annotated field locator")
    public void wire(final Object instance) {
        checkNotNull(instance);
    }
}
