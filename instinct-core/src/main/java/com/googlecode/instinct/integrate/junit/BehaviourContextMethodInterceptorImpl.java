/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.integrate.junit;

import java.lang.reflect.Method;
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import au.net.netstorm.boost.edge.java.lang.reflect.DefaultEdgeMethod;
import au.net.netstorm.boost.edge.java.lang.reflect.EdgeMethod;
import junit.framework.Test;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class BehaviourContextMethodInterceptorImpl implements MethodInterceptor {
    private EdgeClass edgeClass = new DefaultEdgeClass();
    private EdgeMethod edgeMethod = new DefaultEdgeMethod();
    private final Test delegate;

    public BehaviourContextMethodInterceptorImpl(final Test delegate) {
        this.delegate = delegate;
    }

    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) {
        final Method invokee = edgeClass.getMethod(delegate.getClass(), method.getName(), method.getParameterTypes());
        return edgeMethod.invoke(invokee, delegate, args);
    }
}
