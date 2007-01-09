package com.googlecode.instinct.test.instance;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Rename this to proxied instance provider")
public final class ProxiedInstanceProvider implements InstanceProvider {
    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        throw new UnsupportedOperationException();
//        return Mocker.mock(cls);
    }
}
