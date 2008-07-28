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

package com.googlecode.instinct.stack

import marker.annotate.{Stub, Specification}
import com.googlecode.instinct.expect.Expect._

final class AnEmptyStackSpeccedUsingScala {
    @Stub var element: Int = 1

    @Specification {val expectedException = classOf[RuntimeException], val withMessage = "Cannot pop an empty stack"}
    def failsWhenPopped {
        EmptyStack.pop
    }

    @Specification {val expectedException = classOf[RuntimeException], val withMessage = "Nothing to see"}
    def failsWhenPeeked {
        EmptyStack.peek
    }

    @Specification
    def returnsNoneWhenSafelyPopped {
        expect.that(EmptyStack.safePop).isEqualTo(None)
    }

    @Specification
    def isNoLongerEmptyAfterPush {
        val stack = EmptyStack.push(element)
        expect.that(stack.peek).isEqualTo(element)
    }
}

object Runner {
    import com.googlecode.instinct.runner.TextRunner._

    def main(args: Array[String]) {
        runContexts(Array(classOf[AnEmptyStackSpeccedUsingScala]))
    }
}
