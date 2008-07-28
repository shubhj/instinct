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

import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification

sealed trait Stack[+A] {
    def push[B >: A](element: B): Stack[B]
    def pop: Stack[A]
    def safePop: Option[Stack[A]]
    def peek: A
    def safePeek: Option[A]
    def map[B, BB >: B](f: A => B): Stack[BB] = this match {
        case EmptyStack => EmptyStack
        case NonEmptyStack(element, elements) => NonEmptyStack(f(element), elements.map(f))
    }
}

final case object EmptyStack extends Stack[Nothing] {
    override def push[B](element: B) = NonEmptyStack(element, this)
    override def pop = error("Cannot pop an empty stack")
    override def safePop = None
    override def peek = error("Nothing to see")
    override def safePeek = None
}

final case class NonEmptyStack[+A](element: A, elements: Stack[A]) extends Stack[A] {
    override def push[B >: A](element: B) = NonEmptyStack(element, this)
    override def pop = elements
    override def safePop = Some(elements)
    override def peek = element
    override def safePeek = Some(element)
}

final class AnEmptyStackInScala {
    @Specification {val expectedException = classOf[RuntimeException], val withMessage = "Cannot pop an empty stack"}
    def failsWhenPopped {
        EmptyStack.pop
    }

    def returnsNoneWhenSafelyPopped {
        expect.that(EmptyStack.safePop).isEqualTo(None)
    }
}

object Runner {
    import com.googlecode.instinct.runner.TextRunner._

    def main(args: Array[String]) {
        runContexts(Array(classOf[AnEmptyStackInScala]))
    }
}
