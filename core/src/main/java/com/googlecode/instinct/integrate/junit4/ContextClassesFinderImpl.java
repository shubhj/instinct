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

package com.googlecode.instinct.integrate.junit4;

import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import com.googlecode.instinct.marker.annotate.ContextClasses;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public final class ContextClassesFinderImpl implements ContextClassesFinder {
    public Collection<Class<?>> getContextClasses(final Class<?> cls) {
        checkNotNull(cls);
        final Collection<Class<?>> classes = new HashSet<Class<?>>();
        final ContextClasses annotation = cls.getAnnotation(ContextClasses.class);
        if (annotation == null) {
            classes.add(cls);
        } else {
            classes.addAll(Arrays.asList(annotation.value()));
        }
        return classes;
    }
}
