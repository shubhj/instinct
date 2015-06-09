<[User's Guide](UsersGuide.md)>

# Specifications #

Instinct is oriented around specifications of behaviour (specifications or specs), that is, you expect the object under test (the subject) to behave in a certain manner. Instinct allows you to specify that in the same way as traditional unit testing frameworks such as JUnit.

Specifications are the methods that invoke the object under scrutiny (the subject) and check its behaviour. They are equivalent to XUnit test methods. Contexts give you a way of grouping specifications that operate within a common context together. For example, a stack object may have many different states, empty, full, half-full, etc. You could think of specifications as needing a context in which to run, where the behaviour only makes sense in that context. Each context should represent a particular context of the test subject, like the full stack example from above. You are encouraged to use more than one behaviour context per test subject, perhaps even within the same class file.

Contexts and specifications should be named so that when read together they form a description of what the specification is attempting to achieve. Consider the following methods and their derived descriptions:

  * `AnEmptyStack.mustBeEmpty()` - An empty stack must be empty;
  * `AnEmptyStack.mustNoLongerBeEmptyAfterPush()` - An empty stack must no longer be empty after push;
  * `AFullStack.mustNoLongerBeFullAfterPop()` - A full stack must no longer be full after pop.

Choosing good names for specifications allows their meaning to be discovered without having to parse the method content.

# Writing A Specification #

Specifications are simply methods contained within a class. In order to run a specification, you must mark it in a manner so that Instinct can find it. Instinct currently supports actors marked using Java annotations.

Instinct supports the following annotations:

  * Context - Marks a class as being a context. This is not required (it is informative only), Instinct will still find and run your specifications without this annotation.
  * Specification - Marks a method as being a specification.
  * BeforeSpecification - Marks a method to be run before each specification is run. You can have as many before specification methods as you like.
  * AfterSpecification - Marks a method to be run after each specification is run. You can have as many after specification methods as you like.

Here is a simple specification.

```
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import static com.googlecode.instinct.marker.annotate.Specification.SpecificationState.PENDING;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AnEmptyStack {
    @Subject private Stack<Object> stack;
    @Dummy private Object object;

    @BeforeSpecification
    void before() {
        object = new Object();
        stack = new StackImpl<Object>();
    }

    @Specification
    void isEmpty() {
        expect.that(stack.isEmpty()).isTrue();
    }

    @Specification
    void isNoLongerBeEmptyAfterPush() {
        stack.push(new Object());
        expect.that(stack.isEmpty()).isFalse();
    }

    @Specification
    void returnTheSameObjectWhenPushed() {
        stack.push(object);
        final Object o = stack.pop();
        expect.that(o).sameInstanceAs(object);
    }

    @Specification(expectedException = IllegalStateException.class, withMessage = "Cannot pop an empty stack")
    void throwsExceptionWhenPoppedWithNoElements() {
        stack.pop();
    }

    @Specification(state = PENDING)
    void hasSomeNewFeatureWeHaveNotThoughtOfYet() {
        expect.that(true).isFalse();
    }
}
```

Here's the code that we've driven out.

```
import java.util.ArrayList;
import java.util.List;

public final class StackImpl<T> implements Stack<T> {
    private final List<T> objects = new ArrayList<T>();

    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public void push(final T t) {
        objects.add(t);
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an empty stack");
        }
        return objects.remove(0);
    }
}
```

# Specification methods #

TODO: Write about the different methods, Specification, BeforeSpecification, AfterSpecification. How many can you have, how do you mark them?

# Specification lifecycle #

TODO - before spec, after spec. Talk about the way in which tests are executed (the algorithm from the specification runner). Rules around what will and won't be executed, incl. methods & constructors.

# Context inheritance #

TODO Document this, in particular abstract superclasses.

<[User's Guide](UsersGuide.md)>