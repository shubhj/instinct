package com.googlecode.instinct.internal.mock.instance;

import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.mock.Mocker.mock;

@Suggest("May be better to use dynamic proxies rather than mocks?")
public final class MockInstanceProvider implements InstanceProvider {
    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        return mock(cls);
    }
}
