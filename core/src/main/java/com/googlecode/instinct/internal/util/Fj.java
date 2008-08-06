/*
 * Copyright 2006-2008 Workingmouse
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

package com.googlecode.instinct.internal.util;

import fj.data.Array;
import fj.data.Java;
import fj.data.List;
import fj.F;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public final class Fj {
    private Fj() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings({"TypeMayBeWeakened"})
    public static <T> List<T> toFjList(final Collection<T> collection) {
        if (collection instanceof HashSet) {
            return toFjList((HashSet<T>) collection);
        } else if (collection instanceof ArrayList) {
            return toFjList((ArrayList<T>) collection);
        } else {
            throw new RuntimeException("Unknown collection type: " + collection.getClass());
        }
    }

    public static <T> List<T> toFjList(final T... array) {
        return toFjArray(array).toList();
    }

    public static <T> Array<T> toFjArray(final T[] array) {
        return Array.array(array);
    }

    public static F<Boolean, Boolean> not() {
        return new F<Boolean, Boolean>() {
            public Boolean f(final Boolean b) {
                return !b;
            }
        };
    }

    // SUPPRESS IllegalType {
    private static <T> List<T> toFjList(final HashSet<T> set) {
        return Java.<T>HashSet_List().f(set);
    }

    private static <T> List<T> toFjList(final ArrayList<T> list) {
        return Java.<T>ArrayList_List().f(list);
    }
    // } SUPPRESS IllegalType
}
