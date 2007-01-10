package com.googlecode.instinct.test.instance;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.mock.Mockery;

@Suggest("Use dynamic proxies rather than mocks?")
public final class ProxiedInstanceProvider implements InstanceProvider {
    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        return Mockery.mock(cls);
    }
}
