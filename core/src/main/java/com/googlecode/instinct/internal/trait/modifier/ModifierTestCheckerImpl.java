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

public final class ModifierTestCheckerImpl implements ModifierTestChecker {
    private static final String NOT_PUBLIC = "is not public";
    private static final String NOT_PRIVATE = "is not private";
    private static final String NOT_FINAL = "is not final";
    private static final String NOT_SYNCHRONIZED = "is not synchronized";
    private static final String NOT_CONCRETE = "is not concrete";
    private static final String NOT_STATIC = "is not static";
    private static final String NOT_INSTANCE = "is not instance";
    private final ModifierTestUtil modifier = new ModifierTestUtilImpl();

    public void checkPublic(final Member member) {
        final boolean isPublic = modifier.isPublic(member);
        check(member, NOT_PUBLIC, isPublic);
    }

    public void checkPrivate(final Member member) {
        final boolean isPrivate = modifier.isPrivate(member);
        check(member, NOT_PRIVATE, isPrivate);
    }

    public void checkFinal(final Member member) {
        final boolean isFinal = modifier.isFinal(member);
        check(member, NOT_FINAL, isFinal);
    }

    public void checkSynchronized(final Member member) {
        final boolean isSynchronized = modifier.isSynchronized(member);
        check(member, NOT_SYNCHRONIZED, isSynchronized);
    }

    public void checkStatic(final Member member) {
        final boolean isStatic = modifier.isStatic(member);
        check(member, NOT_STATIC, isStatic);
    }

    public void checkInstance(final Member member) {
        final boolean isInstance = modifier.isInstance(member);
        check(member, NOT_INSTANCE, isInstance);
    }

    public void checkPrivateFinalInstance(final Member member) {
        checkPrivate(member);
        checkFinal(member);
        checkInstance(member);
    }

    public <T> void checkPublic(final Class<T> cls) {
        final boolean isPublic = modifier.isPublic(cls);
        check(cls, NOT_PUBLIC, isPublic);
    }

    public <T> void checkFinal(final Class<T> cls) {
        final boolean isFinal = modifier.isFinal(cls);
        check(cls, NOT_FINAL, isFinal);
    }

    public <T> void checkConcrete(final Class<T> cls) {
        final boolean isConcrete = modifier.isConcrete(cls);
        check(cls, NOT_CONCRETE, isConcrete);
    }

    private void check(final Member member, final String comment, final boolean ok) {
        final String name = member.getName();
        check(name, comment, ok);
    }

    private <T> void check(final Class<T> cls, final String comment, final boolean ok) {
        final String name = cls.getSimpleName();
        check(name, comment, ok);
    }

    private void check(final String name, final String comment, final boolean ok) {
        if (!ok) {
            throw new AssertionError(name + " " + comment);
        }
    }
}
