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

package com.googlecode.instinct.internal.util.boost;

import java.lang.reflect.Member;

public interface ModifierTestUtil {
    boolean isPublic(Member member);

    boolean isProtected(Member member);

    boolean isPrivate(Member member);

    boolean isPublicInstance(Member member);

    boolean isStatic(Member member);

    boolean isInstance(Member member);

    boolean isFinal(Member member);

    boolean isSynchronized(Member member);

    // Why the hell doesn't j.l.Class implement j.l.r.Member?
    // Then we would not need to double up like this!

    <T> boolean isPublic(Class<T> cls);

    <T> boolean isFinal(Class<T> cls);

    <T> boolean isAbstract(Class<T> cls);

    <T> boolean isConcrete(Class<T> cls);

    <T> boolean isInterface(Class<T> cls);

    <T> boolean isSynchronized(Class<T> cls);
}
