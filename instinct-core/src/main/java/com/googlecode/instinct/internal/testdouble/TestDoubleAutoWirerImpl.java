package com.googlecode.instinct.internal.testdouble;

import com.googlecode.instinct.core.annotate.Dummy;
import com.googlecode.instinct.core.naming.DummyNamingConvention;
import com.googlecode.instinct.internal.aggregate.locate.MarkedFieldLocator;
import com.googlecode.instinct.internal.aggregate.locate.MarkedFieldLocatorImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.instance.ObjectFactory;
import com.googlecode.instinct.internal.util.instance.ObjectFactoryImpl;

public final class TestDoubleAutoWirerImpl implements TestDoubleAutoWirer {
    private ObjectFactory objectFactory = new ObjectFactoryImpl();
    private MarkedFieldLocator fieldLocator = new MarkedFieldLocatorImpl();

    @Suggest("Come back here after creating an marked field locator & use it here")
    public void wire(final Object instance) {
        checkNotNull(instance);
        fieldLocator.locateAll(instance.getClass(), Dummy.class, objectFactory.create(DummyNamingConvention.class));
        // check fields - non-final & null value
        // set accessible
        // Creator double & set field
    }
}
