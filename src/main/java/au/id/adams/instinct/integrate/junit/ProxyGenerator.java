package au.id.adams.instinct.integrate.junit;

import net.sf.cglib.proxy.MethodInterceptor;

public interface ProxyGenerator {
    <T> Object newProxy(Class<T> classToProxy, MethodInterceptor methodInterceptor);
}
