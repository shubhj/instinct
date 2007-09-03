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

package com.googlecode.instinct.test.checker;

import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectException;
import static com.googlecode.instinct.test.checker.ExceptionTestChecker.expectMessageContains;

@Suggest("Remove the use of this class!!!")
public final class AssertThrowsChecker {
    private AssertThrowsChecker() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Throwable> Throwable assertThrows(final Class<T> expectedException, final String message, final Runnable block) {
        return expectException(expectedException, message, block);
    }

    public static <T extends Throwable> Throwable assertThrows(final Class<T> expectedException, final Runnable block) {
        return expectException(expectedException, block);
    }

    public static void assertMessageContains(final Throwable t, final String fragment) {
        expectMessageContains(t, fragment);
    }
}
