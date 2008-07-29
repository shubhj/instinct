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

import expect.Expect._
import marker.annotate.{Specification, Stub}

final class AnEmptyStackSpeccedUsingScala {
    @Stub var element: Int = 1

    @Specification {val expectedException = classOf[RuntimeException], val withMessage = "Cannot pop an empty stack"}
    def failsWhenPopped {
        EmptyStack.pop
//        expect.that(EmptyStack.pop).willThrow(classOf[RuntimeException]).withMessage("Cannot pop an empty stack")
//        expect.that(EmptyStack.pop).willThrow(new RuntimeException("Cannot pop an empty stack"))
//        expect.that(EmptyStack.pop).errorsWith("Cannot pop an empty stack")
    }

    @Specification {val expectedException = classOf[RuntimeException], val withMessage = "Nothing to see"}
    def failsWhenPeeked {
        EmptyStack.peek
    }

    @Specification
    def returnsNoneWhenSafelyPopped {
        expect.that(EmptyStack.safePop).isEqualTo(None)
//        expect.that(EmptyStack.safePop.asInstanceOf[fj.data.Option[_]]).isNone
//        EmptyStack.safePop.must.equal(None)
//        EmptyStack.safePop.mustNot.equal(Some(element))
//        expect.that(EmptyStack.safePop).isEqualTo(None)
//        expect.that(EmptyStack.safePop).isNone
//        expect.that(EmptyStack.safePop).isSome(element)
    }

    @Specification
    def isNoLongerEmptyAfterPush {
        val stack = EmptyStack.push(element)
        expect.that(stack.peek).isEqualTo(element)
//        expect.that(stack.safePeek).isSome(element)
    }
}
