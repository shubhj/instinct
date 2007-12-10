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

import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;

public interface CgLibEnhancer {
    void setCallbackTypes(Class<? extends Callback>... callbackTypes);

    void setCallbackFilter(CallbackFilter filter);

    void setNamingPolicy(NamingPolicy namingPolicy);

    <T> void setSuperclass(Class<T> superclass);

    <T> void setInterface(Class<T> iface);

    Class<?> createClass();
}
