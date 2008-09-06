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

package com.googlecode.instinct.internal.util;

import java.util.regex.Pattern;

public final class ParamChecker {
    private static final NullMaster NULL_MASTER = new NullMasterImpl();
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

    private ParamChecker() {
        throw new UnsupportedOperationException();
    }

    public static void checkNotNull(final Object... params) {
        NULL_MASTER.check(params);
    }

    public static void checkNotWhitespace(final String... params) {
        checkNotNull((Object) params);
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null || params[i].trim().length() == 0) {
                throw new IllegalArgumentException("Parameter " + i + " should not be null, the empty string or exclusively whitespace");
            }
        }
    }

    public static <T> void checkIsInterface(final Class<T> type) {
        if (!type.isInterface()) {
            throw new IllegalArgumentException(type.getSimpleName() + " must be an interface not a concrete class");
        }
    }

    public static <T> void checkIsConcreteClass(final Class<T> type) {
        if (type.isInterface()) {
            throw new IllegalArgumentException(type.getSimpleName() + " must be a concrete class not an interface");
        }
    }

    public static void checkNoWhitespace(final String... params) {
        for (int i = 0; i < params.length; i++) {
            final String param = params[i];
            if (param != null && WHITESPACE_PATTERN.matcher(param).find()) {
                throw new IllegalArgumentException("Parameter " + i + " should not contain whitespace");
            }
        }
    }

}
