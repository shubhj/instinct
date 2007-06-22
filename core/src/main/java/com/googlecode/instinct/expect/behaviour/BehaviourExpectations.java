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

package com.googlecode.instinct.expect.behaviour;

import org.jmock.internal.ExpectationBuilder;

/**
 * Behaviour expectation API.
 * Behaviour expectations are expectations of a class' behaviour, made using mock collaborators. The Instinct behaviour expectation API is built on
 * top of jMock 2, and provides three options for use:
 * <pre>
 * final List&lt;String&gt; strings = mock(List.class);
 *
 * // Instinct DSL (not implemented yet)
 * expect.that(one(strings).add("abc")).will(returnValue(true));
 *
 * // jMock 1 style (not implemented yet)
 * expect.that(strings, once()).method("add").with(eq("abc")).will(returnValue(true));
 *
 * // jMock 2 style
 * expect.that(new Expectations() {{
 *     one(strings).add("abc"); will(returnValue(true));
 * }});
 * </pre>
 * Mock collablorators are created automatically using the {@link com.googlecode.instinct.marker.annotate.Mock} annotation or manually using
 * {@link com.googlecode.instinct.expect.Mocker12#mock(Class)}.
 */
public interface BehaviourExpectations {
    /**
     * Sets behaviour expectations using a jMock 2 syntax.
     *
     * @param expectations The jMock2 expectations to set.
     */
    void that(ExpectationBuilder expectations);
}
