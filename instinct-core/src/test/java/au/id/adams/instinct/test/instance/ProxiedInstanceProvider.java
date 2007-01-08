package au.id.adams.instinct.test.instance;

import au.id.adams.instinct.test.mock.MockCreator;
import au.id.adams.instinct.internal.util.Suggest;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;

@Suggest("Rename this to proxied instance provider")
public final class ProxiedInstanceProvider implements InstanceProvider {

    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        return MockCreator.createMock(cls);
    }
}
