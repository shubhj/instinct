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

package com.googlecode.instinct.internal.locate;

import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.TechNote;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class OverridenMethodRemoverImpl implements OverridenMethodRemover {

    @Suggest("Find a better way to filter this.")
    public Collection<Method> removeOverridenMethods(final Collection<Method> unfilteredMethods) {
        final Set<DuplicateMethodRemover> methodFilter = new HashSet<DuplicateMethodRemover>();

        for (final Method method : unfilteredMethods) {
            methodFilter.add(new DuplicateMethodRemoverImpl(method));
        }
        final Set<Method> filteredMethods = new HashSet<Method>();

        for (final DuplicateMethodRemover filtered : methodFilter) {
            filteredMethods.add(filtered.getMethod());
        }
        return filteredMethods;
    }

    private static final class DuplicateMethodRemoverImpl implements DuplicateMethodRemover {
        private final Method method;

        private DuplicateMethodRemoverImpl(final Method method) {
            this.method = method;
        }

        @TechNote("generate the hashCode based on the method name, as methods with the same name on different classes have different hashCodes.")
        @Override
        public int hashCode() {
            return method.getName().hashCode();
        }

        @TechNote("equals() is only called if the hashCodes are equal. Implied by the spec but tricky non-the-less.")
        @Override
        public boolean equals(final Object object) {
            if (object == null || !(object instanceof DuplicateMethodRemover)) {
                return false;
            }
            final Method methodToCompare = ((DuplicateMethodRemover) object).getMethod();
            return method.getName().equals(methodToCompare.getName()) || method.equals(object);
        }

        public Method getMethod() {
            return method;
        }
    }

    private interface DuplicateMethodRemover {

        Method getMethod();
    }
}
