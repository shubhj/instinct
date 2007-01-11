package com.googlecode.instinct.internal.mock.instance;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.mock.Mocker;

@Suggest("Use dynamic proxies rather than mocks?")
public final class ProxiedInstanceProvider implements InstanceProvider {
    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        return Mocker.mock(cls);
    }
}
