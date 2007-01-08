package au.id.adams.instinct.test.instance;

import au.id.adams.instinct.test.mock.MockCreator;
import au.net.netstorm.boost.nursery.instance.InstanceProvider;
import com.workingmouse.test.mock.MockFactory;
import com.workingmouse.test.mock.MockFactoryImpl;

@Suggest("Rename this to proxied instance provider")
public final class MockingInstanceProvider implements InstanceProvider {

    @SuppressWarnings({"RawUseOfParameterizedType", "unchecked"})
    public Object newInstance(final Class cls) {
        return MockCreator.createMock(cls);
    }
}
