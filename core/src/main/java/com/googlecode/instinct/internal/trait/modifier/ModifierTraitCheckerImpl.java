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

package com.googlecode.instinct.internal.trait.modifier;

import java.lang.reflect.Member;

public final class ModifierTraitCheckerImpl implements ModifierTraitChecker {
    private final ModifierUtil modifier = new ModifierUtilImpl();

    public void checkPublic(final Member member) {
        check(member, "is not public", modifier.isPublic(member));
    }

    public void checkPrivate(final Member member) {
        check(member, "is not private", modifier.isPrivate(member));
    }

    public void checkFinal(final Member member) {
        check(member, "is not final", modifier.isFinal(member));
    }

    public void checkSynchronized(final Member member) {
        check(member, "is not synchronized", modifier.isSynchronized(member));
    }

    public void checkStatic(final Member member) {
        check(member, "is not static", modifier.isStatic(member));
    }

    public void checkInstance(final Member member) {
        check(member, "is not instance", modifier.isInstance(member));
    }

    public void checkPrivateFinalInstance(final Member member) {
        checkPrivate(member);
        checkFinal(member);
        checkInstance(member);
    }

    public <T> void checkPublic(final Class<T> cls) {
        check(cls, "is not public", modifier.isPublic(cls));
    }

    public <T> void checkFinal(final Class<T> cls) {
        check(cls, "is not final", modifier.isFinal(cls));
    }

    public <T> void checkAbstract(final Class<T> cls) {
        check(cls, "is not abstract", modifier.isAbstract(cls));
    }

    public <T> void checkConcrete(final Class<T> cls) {
        check(cls, "is not concrete", modifier.isConcrete(cls));
    }

    private void check(final Member member, final String comment, final boolean ok) {
        final String name = member.getName();
        if (!ok) {
            throw new AssertionError(name + " " + comment);
        }
    }

    private <T> void check(final Class<T> cls, final String comment, final boolean ok) {
        if (!ok) {
            throw new AssertionError(cls.getSimpleName() + " " + comment);
        }
    }
}
