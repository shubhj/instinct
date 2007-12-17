/*
 * Copyright 2006-2007 Workingmouse
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

package com.googlecode.instinct.internal.util.proxy;

import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import java.util.List;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

public final class CgLibEnhancerImpl implements CgLibEnhancer {
    private final Enhancer enhancer = new AllConstructorsEnhancer();

    public void setCallbackTypes(final Class<? extends Callback>... callbackTypes) {
        checkNotNull((Object[]) callbackTypes);
        enhancer.setCallbackTypes(callbackTypes);
    }

    public void setCallbackFilter(final CallbackFilter filter) {
        checkNotNull(filter);
        enhancer.setCallbackFilter(filter);
    }

    public void setNamingPolicy(final NamingPolicy namingPolicy) {
        checkNotNull(namingPolicy);
        enhancer.setNamingPolicy(namingPolicy);
    }

    public <T> void setSuperclass(final Class<T> superclass) {
        checkNotNull(superclass);
        enhancer.setSuperclass(superclass);
    }

    public <T> void setInterface(final Class<T> iface) {
        checkNotNull(iface);
        enhancer.setInterfaces(new Class[]{iface});
    }

    public Class<?> createClass() {
        return enhancer.createClass();
    }

    private static final class AllConstructorsEnhancer extends Enhancer {
        @SuppressWarnings({"RawUseOfParameterizedType"})
        @Override
        public void filterConstructors(final Class sc, final List constructors) {
            // Note. We don't want to filter out private constructors.
        }
    }
}
