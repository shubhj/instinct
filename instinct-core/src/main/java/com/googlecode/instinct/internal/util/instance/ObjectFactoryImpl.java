package com.googlecode.instinct.internal.util.instance;

import java.lang.reflect.Constructor;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeConstructor;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeConstructor;
import static com.googlecode.instinct.internal.util.ParamChecker.checkIsConcreteClass;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;

public final class ObjectFactoryImpl implements ObjectFactory {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();

    public <T> T create(final Class<T> concreteClass) {
        checkNotNull(concreteClass);
        checkIsConcreteClass(concreteClass);
        return create(concreteClass, new Class<?>[]{}, new Object[]{});
    }

    @SuppressWarnings({"unchecked"})
    public <T> T create(final Class<T> concreteClass, final Class<?>[] types, final Object[] values) {
        checkNotNull(concreteClass, types, values);
        checkIsConcreteClass(concreteClass);
        final Constructor<T> constructor = edgeClass.getConstructor(concreteClass, types);
        return (T) edgeConstructor.newInstance(constructor, values);
    }
}
