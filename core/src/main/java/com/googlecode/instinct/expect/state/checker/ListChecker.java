/*
 * Copyright 2008 Tom Adams
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

package com.googlecode.instinct.expect.state.checker;

import fj.F;
import fj.data.List;
import org.hamcrest.Matcher;

public interface ListChecker<T> extends ObjectChecker<List<T>>, ContainerChecker<T>, SizeChecker {
    @SuppressWarnings({"AbstractMethodOverridesAbstractMethod"})
    void isEqualTo(List<T> list);

    @SuppressWarnings({"AbstractMethodOverridesAbstractMethod"})
    void isNotEqualTo(List<T> list);

    void contains(F<T, Boolean> matching);

    void doesNotContain(F<T, Boolean> matching);

    void allItemsMatch(Matcher<T> matcher);

    void allItemsMatch(F<T, Boolean> matching);

    void allItemsMatch(T item);
}
