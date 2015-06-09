<[User's Guide](UsersGuide.md)>

# JUnit Integration #

## JUnit 4 ##

Instinct supports JUnit 4's `@RunWith` annotation by providing its own runner. This runner allows Instinct specifications to be run by JUnit infrastructure such as IDE plugins. To allow your specifications to run within JUnit, add an `@RunWith(InstinctRunner.class)` annotation to your context class as follows:

```
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ANonEmptyStack {
    private static final int SIZE = 10;
    private Stack<Integer> stack;

    @BeforeSpecification
    void fillUpStack() {
        stack = new StackImpl<Integer>();
        for (int i = 0; i < SIZE; i++) {
            stack.push(i);
        }
    }

    @Suggest("This is bogus, need an isFull() method.")
    @Specification
    void mustNoLongerBeFullAfterPop() {
        stack.pop();
        expect.that(stack.isEmpty()).isFalse();
    }

    @Specification
    void isNoLongerFullAfterPoppingAllElements() {
        for (int i = 0; i < SIZE; i++) {
            stack.pop();
        }
        expect.that(stack.isEmpty()).isTrue();
    }
}
```

You can also create JUnit 4 suites as follows:

```
@RunWith(InstinctRunner.class)
@ContextClasses(value = {AnEmptyStack.class, ANonEmptyStack.class, AnEmptyMagazineRack.class, AGlossyMagazine.class})
public final class StackJUnit4Suite {
    private StackJUnit4Suite() {
        throw new UnsupportedOperationException();
    }
}
```

### IDEs ###

The following screenshot highlights the integration of failing and pending specifications in IntelliJ IDEA.

![http://instinct.googlecode.com/svn/wiki/images/InstinctJUnitIntegrationFailure.png](http://instinct.googlecode.com/svn/wiki/images/InstinctJUnitIntegrationFailure.png)

The following screenshot highlights the integration of successful and pending specifications in IntelliJ IDEA.

![http://instinct.googlecode.com/svn/wiki/images/InstinctJUnitIntegrationSuccess.png](http://instinct.googlecode.com/svn/wiki/images/InstinctJUnitIntegrationSuccess.png)

### Ant ###

Instinct specifications can also be used from with Ant's JUnit 4 task as follows:

```
<junit failureproperty="tests-failed" fork="true" forkmode="perBatch">
    <classpath refid="spec.class.path"/>
    <test name="com.googlecode.instinct.example.junit.StackJUnit4Suite"/>
    <formatter type="brief" usefile="false"/>
</junit>
```


## JUnit 3 ##

### IDEs ###

Instinct provides JUnit 3 integration via the `JUnitTestSuiteBuilderImpl` class. The following is an example of using this class to automatically build a suite of contexts to run. Note that the `AClassInSpecTree.class`class must be a class in your specification tree, Instinct use the location of this class to find specifications to run.

```
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.suite.AllTestSuite;
import junit.framework.Test;

public final class JUnitSuite {
    private JUnitSuite() {
        throw new UnsupportedOperationException();
    }

    public static Test suite() {
        return new JUnitTestSuiteBuilderImpl(AClassInSpecTree.class).buildSuite("Behaviour Contexts");
    }
}
```

JUnit 3 suites can also be built manually as follows:

```
public final class StackJUnitSuite {
    private StackJUnitSuite() {
        throw new UnsupportedOperationException();
    }

    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Instinct JUnit 3 Integration - Stack Example");
        addContextsToSuite(suite, AnEmptyStack.class, ANonEmptyStack.class, AnEmptyMagazineRack.class, AGlossyMagazine.class);
        return suite;
    }

    private static void addContextsToSuite(final TestSuite suite, final Class<?>... contextClasses) {
        for (final Class<?> contextClass : contextClasses) {
            suite.addTest(newSuite(contextClass));
        }
    }

    private static <T> TestSuite newSuite(final Class<T> contextType) {
        return new ContextTestSuite(contextType);
    }
}
```

### Ant ###

Instinct specifications can also be used from with Ant's JUnit 3 task as follows:

```
<junit failureproperty="tests-failed" fork="true" forkmode="perBatch">
    <classpath refid="spec.class.path"/>
    <test name="com.googlecode.instinct.example.junit.StackJUnitSuite"/>
    <formatter type="brief" usefile="false"/>
</junit>
```

<[User's Guide](UsersGuide.md)>