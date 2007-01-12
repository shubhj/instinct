package com.googlecode.instinct.internal.mock;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import com.googlecode.instinct.internal.mock.instance.ConcreteInstanceProvider;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.AssertionFailedError;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.Callback;
import org.jmock.core.AbstractDynamicMock;
import org.jmock.core.Invocation;
import org.jmock.core.LIFOInvocationDispatcher;

@Suggest("A hack to allow JMock to generate mocks for classes that don't have no-args constructors. Maybe a newer version of JMock does this?")
public final class ConcreteClassMock extends AbstractDynamicMock implements MethodInterceptor {
    private Object proxy;

    @Suggest("This is a bit yuck")
    public <T> ConcreteClassMock(final Class<T> mockedType, final String name) {
        super(mockedType, name, new LIFOInvocationDispatcher());
        checkIsNotNonStaticInnerClass(mockedType);
        createMock(mockedType);
    }

    private <T> void createMock(final Class<T> mockedType) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(mockedType.getClassLoader());
        enhancer.setSuperclass(mockedType);
        enhancer.setCallbackType(getClass());
        enhancer.setUseCache(false);
        final Class<?> proxiedClass = enhancer.createClass();
        Enhancer.registerCallbacks(proxiedClass, new Callback[]{this});
        proxy = new ConcreteInstanceProvider().newInstance(proxiedClass);
    }

    private <T> void checkIsNotNonStaticInnerClass(final Class<T> mockedType) {
        if (mockedType.getDeclaringClass() != null && !Modifier.isStatic(mockedType.getModifiers())) {
            throw new AssertionFailedError("cannot mock non-static inner class " + mockedType.getName());
        }
    }

    @Override
    public Object proxy() {
        return proxy;
    }

    // DEBT IllegalCatch {
    @Suggest("Handle this exception better.")
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) {
        try {
            return this.proxy == null ? proxy.invokeSuper(obj, args) : mockInvocation(new Invocation(this.proxy, method, args));
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
    // } DEBT IllegalCatch
}
