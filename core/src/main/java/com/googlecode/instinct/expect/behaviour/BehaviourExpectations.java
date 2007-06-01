package com.googlecode.instinct.expect.behaviour;

/**
 * Behaviour expectation API.
 * Behaviour expectations are expectations of a class' behaviour, made using mock collaborators. The Instinct behaviour expectation API is built on
 * top of jMock 2, and provides three options for use:
 * <pre>
 * final List&lt;String&gt; strings = mock(List.class);
 *
 * // Instinct DSL
 * expect.that(one(strings).add("abc")).will(returnValue(true));
 *
 * // jMock 1 style
 * expect.that(strings, once()).method("add").with(eq("abc")).will(returnValue(true));
 *
 * // jMock 2 style
 * expect.that(new Expectations() {{
 *     one(strings).add("abc"); will(returnValue(true));
 * }});
 * </pre>
 * Mock collablorators are created automatically using the {@link com.googlecode.instinct.marker.annotate.Mock} annotation or manually using
 * {@link com.googlecode.instinct.expect.Mocker#mock(Class)}.
 */
public interface BehaviourExpectations {
}
