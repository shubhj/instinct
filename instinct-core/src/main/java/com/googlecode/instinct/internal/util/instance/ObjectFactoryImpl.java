package com.googlecode.instinct.internal.util.instance;

import java.lang.reflect.Constructor;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeConstructor;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeConstructor;
import static com.googlecode.instinct.internal.util.ParamChecker.checkIsConcreteClass;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"Add type inference of the objects.",
        "Get the constructor of the concrete type where the number of arguments matches the passed in values.",
        "Then work out if the objects are of compatible types."})
public final class ObjectFactoryImpl implements ObjectFactory {
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final EdgeConstructor edgeConstructor = new DefaultEdgeConstructor();

    public <T> T create(final Class<T> concreteClass) {
        checkNotNull(concreteClass);
        checkIsConcreteClass(concreteClass);
        return create(concreteClass, new Class<?>[]{}, new Object[]{});
    }

    public <T> T create(final Class<T> concreteClass, final Object... constructorArgumentValues) {
        checkNotNull(concreteClass, constructorArgumentValues);
//        Constructor<T> bestConstructor = findConstructor(concreteClass, );
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public <T, U> T create(final Class<T> concreteClass, final Class<U>[] types, final Object[] values) {
        checkNotNull(concreteClass, types, values);
        checkIsConcreteClass(concreteClass);
        final Constructor<T> constructor = edgeClass.getConstructor(concreteClass, types);
        return (T) edgeConstructor.newInstance(constructor, values);
    }
}
