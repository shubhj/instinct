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

package com.googlecode.instinct.sandbox.behaviourexpect;

import com.googlecode.instinct.internal.util.Suggest;

@Suggest({"Include other cardinality methods, from jMock2.Expectations here. Also see mockery.",
        "Include jMock 2 cardinality methods??"})
public interface MethodInvocationMatcher {
    //extends NameMatchBuilder {

    <T> T one(final T mockedObject);

//    @Suggest("Change return type to jMock2 class?")
//    ArgumentsMatchBuilder method(String name);
//
//    @Suggest("Change return type to jMock2 class?")
//    ArgumentsMatchBuilder method(Constraint nameConstraint);
//
//    @Suggest("Change return type to jMock2 class?")
//    IdentityBuilder will(Stub stubAction);
}
