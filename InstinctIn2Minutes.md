# Two Minute Introduction #

More comprehensive information is available in the [user's guide](UsersGuide.md).

## Basics ##

Following the normal TDD spec/code/refactor cycle, start with a simple example that expresses the expected behaviour.

```
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Subject;

public final class AnEmptyStack {
    @Subject private Stack<Object> stack;

    @BeforeSpecification
    void before() {
        stack = new StackImpl<Object>();
    }
}
```

This won't compile, so we write the code to make it pass:

```
public interface Stack<T> {
}

public final class StackImpl<T> implements Stack<T> {
}
```

Now let's add some specifications of behaviour.

```
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;

public final class AnEmptyStack {
    @Subject private Stack<Object> stack;

    @BeforeSpecification
    void setUp() {
        stack = new StackImpl<Object>();
    }

    @Specification
    void mustBeEmpty() {
        expect.that(stack.isEmpty()).isEqualTo(true);
    }
}
```

Now we can run the example and watch it fail.

```
$ java -cp $CLASSPATH com.googlecode.instinct.runner.CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack

AnEmptyStack
- mustBeEmpty (FAILED)

        java.lang.AssertionError: 
        Expected: <true>
            got : <false>

                at org.hamcrest.MatcherAssert.assertThat(MatcherAssert.java:14)
                at com.googlecode.instinct.internal.edge.org.hamcrest.MatcherAssertEdgeImpl.expectThat(MatcherAssertEdgeImpl.java:25)
                at com.googlecode.instinct.expect.state.ObjectCheckerImpl.equalTo(ObjectCheckerImpl.java:40)
                at com.googlecode.instinct.example.stack.AnEmptyStack.mustBeEmpty(AnEmptyStack.java:21)
                ...
```

Now write just enough code to make it pass:

```
public final class StackImpl<T> implements Stack<T> {
    private final List<T> objects = new ArrayList<T>();

    public boolean isEmpty() {
        return true;
    }
}
```

Run the example and watch it pass.

```
$ java -cp $CLASSPATH com.googlecode.instinct.runner.CommandLineRunner com.googlecode.instinct.example.stack.AnEmptyStack

AnEmptyStack
- mustBeEmpty

1 specifications, 0 failures
Finished in 0.03 seconds
```

Now we can commit and refactor!

## JUnit Integration ##

For IDE support, it's easier to use the JUnit 4 integration. Using it is easy, just add an `@RunWith` annotation to your class as follows (more information is available on the [JUnit integration](JUnitIntegration.md) page):

```
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;

@RunWith(InstinctRunner.class)
public final class AnEmptyStack {
    @Subject private Stack<Object> stack;

    @BeforeSpecification
    void setUp() {
        stack = new StackImpl<Object>();
    }

    @Specification
    void mustBeEmpty() {
        expect.that(stack.isEmpty()).isEqualTo(true);
    }
}
```

## Ant Integration ##

Here's a summarised version of an Instinct Ant build, more information is available on the  [Ant integration](AntIntegration.md) page.

```
<path id="spec.class.path">
    <path refid="main.class.path"/>
    <fileset dir="${basedir}">
        <include name="${lib.dir}/cglib-nodep-*.jar"/>
        <include name="${lib.dir}/jmock-*.jar"/>
        <include name="${lib.dir}/objenesis-*.jar"/>
        <include name="${lib.dir}/ant-1.*.jar"/>
        <include name="${lib.dir}/hamcrest-all-*.jar"/>
        <include name="${lib.dir}/junit-*.jar"/>
    </fileset>
    <pathelement location="${spec.classes.dir}"/>
</path>

<!-- Execute the specifications -->
<target name="run-specs">
    <taskdef resource="instincttask.properties" classpathref="spec.class.path"/>
    <instinct failureproperty="specs-failed">
        <classpath refid="spec.class.path"/>
        <specifications dir="${spec.classes.dir}"/>
        <formatter type="brief"/>
        <formatter type="xml" toDir="${spec.reports.dir}"/>
    </instinct>
</target>

<!-- Generate the specification report -->
<target name="report">
    <taskdef resource="instincttask.properties" classpathref="spec.class.path"/>
    <instinct-report file="${spec.reports.dir}/report.html">
        <fileset dir="${spec.reports.dir}">
            <include name="SPEC-*.xml"/>
        </fileset>
    </instinct-report>
</target>

```