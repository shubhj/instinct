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

import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

public final class SignedClassSafeNamingPolicy implements NamingPolicy {
    private static final String INSTINCT_GEN_PACKAGE = "com.googlecode.instinct.gen.";
    private final NamingPolicy defaultNamingPolicy = new DefaultNamingPolicy();

    public String getClassName(final String prefix, final String source, final Object key, final Predicate names) {
        checkNotNull(prefix, source, key, names);
        return INSTINCT_GEN_PACKAGE + defaultNamingPolicy.getClassName(prefix, source, key, names);
    }
}
