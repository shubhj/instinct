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

package com.googlecode.instinct.internal.expect.state.matcher;

import com.googlecode.instinct.internal.util.Suggest;

@Suggest("Turn this into a Hamcrest matcher")
public interface AssertException {
    <T> Throwable assertWraps(Class<T> expectedException, Throwable wrapperException);

    <T> Throwable assertWraps(Class<T> expectedException, String expectedMessage, Throwable wrapperException);

    <T> Throwable assertWraps(Class<T> expectedExceptionClass, Throwable wrapperException, int depthExceptionShouldAppearAt);

    <T> Throwable assertWraps(Class<T> expectedExceptionClass, String expectedMessage, Throwable wrapperException, int depthExceptionShouldAppearAt);

    <T> void checkExceptionClass(Class<T> expectedExceptionClass, Throwable actual);

    <T> void checkExceptionMessage(String expectedMessage, Throwable actual);

    <T> Throwable assertThrows(Class<T> expectedException, String message, Runnable block);

    <T> Throwable assertThrows(Class<T> expectedException, Runnable block);

    void assertMessageContains(Throwable t, String fragment);
}
