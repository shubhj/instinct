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

public interface ModifierTestChecker {
    void checkPublic(Member member);

    void checkPrivate(Member member);

    void checkFinal(Member member);

    void checkSynchronized(Member member);

    void checkStatic(Member member);

    void checkInstance(Member member);

    void checkPrivateFinalInstance(Member member);

    <T> void checkPublic(Class<T> cls);

    <T> void checkFinal(Class<T> cls);

    <T> void checkConcrete(Class<T> cls);
}
