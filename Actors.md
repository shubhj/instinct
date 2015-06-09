<[User's Guide](UsersGuide.md)>

# Roles #

Roles help to clarify how an object interacts with its collaborators (more information is available in [Mock Roles, not Objects](http://www.jmock.org/oopsla2004.pdf)).

Instinct encourages you to write separate contexts per "state" of the object under scrutiny (the subject). To make this simpler, Instinct supports creating collaborators as fields in the context class. Instinct also enshrines the role of a collaborator into the specification language, using annotations and naming conventions. This allows provides the following advantages:

  * Simpler infrastructure - Instinct can automatically create actors for you and can automatically verify state and behaviour expectations.
  * Less code - Using fields means there's less setup code in your specifications, clarifying the intent.
  * Clear definition of roles - The roles of collaborators in the current context are clearly defined. If you need to make a collaborator perform more than one role (i.e. it's a mock and a dummy) you may need to create another context.

Here is an example of a context that makes use of these simplifications:

```
class ACsvFileReaderWithOneLineToRead {
    @Subject CsvFileReader csvFileReader;
    @Mock CsvFile csvFile;
    @Mock CsvLineSplitter csvLineSplitter;
    @Stub(auto = false) CsvLine[] parsedLines;
    @Stub(auto = false) String[] splitColumns;
    @Stub String line;

    @BeforeSpecification
    void before() {
        splitColumns = new String[]{"A", "B"};
        parsedLines = new CsvLine[]{new CsvLine("A", "B")};
        csvFileReader = new CsvFileReaderImpl(csvFileReader, csvLineSplitter, csvFile);
    }

    @Specification
    void readsTheLineFromFileAndSplitsItIntoColumns() {
        expect.that(new Expectations() {{
            one(csvFile).hasMoreLines(); will(returnValue(true));
            one(csvFile).readLine(); will(returnValue(line));
            one(csvLineSplitter).split(line); will(returnValue(splitColumns));
            one(csvFile).hasMoreLines(); will(returnValue(false));
            ignoring(csvFile).close();
        }});
        expect.that(csvFileReader.readLines()).isEqualTo(parsedLines);
    }
}
```

Notice how the mocks and stubs have been automatically created (except where a specific value was required) and the mocks have been automatically verified. More information is available on [simplifying mock object testing](http://adams.id.au/blog/2007/01/simplifying-mock-object-testing/).

# Actors #

An actor is an object that has a role in a specification, subjects, dummies, stubs and mocks are all specification actors.

# Subject #

**Note. Auto-creation of subjects is not available in Instinct 0.1.6.**

A subject is the class whose behaviour is under scrutiny. Subjects are created in a specification by marking a field in a context with the `@Subject` annotation, there is currently no subject naming convention.

```
@Subject private Proxy proxy;
```

The subject auto-creation code (subject creator) will attempt to create a subject based only on the type of the subject field (it does not currently support subclasses, etc.). If it cannot create a class for your subject you will see the following error:

> Unable to create value for marked subject field 'proxy' of type example.Proxy.
> This may be because the type of the subject is an interface or abstract class.
> Subject classes can be manually specified using the 'implementationClass' field of the Subject annotation.

If Instinct cannot auto-create your subject, you can disable automatic creation as follows:

```
@Subject(auto = false) private Proxy proxy;
```

If you do turn it off, you will need to manually create your subjects. This is usually done in a before specification method:

```
@Subject(auto = false) private Proxy proxy;

@BeforeSpecification
void before() {
    proxy = new RejectsAllMethodsProxy();
}
```

Alternatively, you can provide an explicit class to instantiate:

```
@Subject(implementation = RejectsAllMethodsProxy.class) private Proxy proxy;
```

If you provide an explicit type it must have a no-args (nullary) constructor (any access modifier will work).

Future versions of the subject creator will attempt to create the class using common naming conventions and (e.g. `ProxyImpl` and `DefaultProxy`) and will also automatically dependency inject

Even if you turn of the automatic creation of a subject, it is still important to mark the role the object as being the subject, as it denotes the role it plays in the specification.

Subjects are initialised before each specification, so no state will be maintained from specification to specification (you would have to manually keep state if you require this).

Instinct does not currently auto-create subjects declared in a superclass of a context.

# Doubles #

A specification double is an implementation of an interface (or extension of a class) that is only used in specifications. Doubles can be manually created (plain classes that implement an interface) or auto-created via a framework.

## Dummies ##

Dummies are objects that get passed around but are never actually used; they usually fill parameters in contexts/specifications where their behaviour may not be important. Dummies will throw exceptions if methods are called on them. Dummies are the simplest form of specification double implementation.

In Instinct, dummies cannot be primitives, final classes or enums, you should use a stub for these.

Dummies are created in a specification by marking a field in a context with the `@Dummy` annotation or by naming the field with a `dummy` prefix (`/^dummy.*/`). The following are two examples of a dummy.

Using an annotation:

```
@Dummy Object object;
```

Using a naming convention:

```
Object dummyObject;
```

If you don't want Instinct to automatically create dummies for you, you can turn them off:

```
@Dummy(auto = false) Object object = new Object();
```

If the dummy field is an array, Instinct will create an array and fill that with dummies of the array's component type:

```
@Dummy Object[] objects;
```

Like with non-array fields, if the component type is itself not dummiable (primitives, final classes and enums) Instinct will give you an error informing you to use stubs for this field.

If you turn of the automatic creation of a dummy, it is still important to mark the role the (dummy) object plays in the specification. This helps other developers know the importance of the object in the specification, and whether the interaction of the subject with the dummy matters in the current context.

Note that there is no way to disable automatic creation of dummies marked with naming conventions. If you use naming conventions and don't want automatic dummy creation you should rename the field so it does not start with `dummy`.

Dummies are initialised before each specification, so no state will be maintained from specification to specification (you would have to manually keep state if you require this).

If you need to create dummies that are not fields, you can use the `DummyCreator` to do so:

```
import com.googlecode.instinct.actor.DummyCreator;

void allowsTheCreationOfDummiesThatAreNotFields() {
    SpecificationDoubleCreator creator = new DummyCreator();
    Object dummy = creator.createDouble(Object.class, "aDummyObject");
}
```

Practically, there are some common types that cannot be dummied as they are final or have methods called when they are shown in error messages (for example in expectation failures). You should use a stub instead of a dummy for the following types:

  * `java.lang.Class`.
  * `java.lang.String`.
  * `java.lang.Integer`, `java.lang.Double`, etc.
  * `java.io.File`.
  * `java.lang.Throwable` and subclasses.

Instinct does not currently auto-create dummies declared in a superclass of a context.

## Stubs ##

**Note. Auto-creation of arrays of stubs and stubbing of interfaces is not available in Instinct 0.1.6.**

Stubs respond to method calls made during a test by providing canned answers. Stubs may record information about calls, such as an email gateway stub that remembers the messages it ‘sent’. Stubs do not fail if methods are called or the order in which they are called (if at all). Stubs are effectively real objects filled with sample data that can be called in your specification or by the production code exercised by a specification.

Stubs are created in a specification by marking a field in a context with the `@Stub` annotation or by naming the field with a `stub` prefix (`/^stub.*/`). The following are two examples of a stub.

Using an annotation:

```
@Stub Object object;
```

Using a naming convention:

```
Object stubObject;
```

If you don't want Instinct to automatically create stubs for you, you can turn them off:

```
@Stub(auto = false) Object object = new Object();
```

If the stub field is an array, Instinct will create an array and fill that with stubs of the array's component type:

```
@Stub Object[] object;
```

If you turn of the automatic creation of a stub, it is still important to mark the role the (stub) object plays in the specification. This helps other developers know the importance of the object in the specification, and whether the interaction of the subject with the stub matters in the current context.

Note that there is no way to disable automatic creation of stubs marked with naming conventions. If you use naming conventions and don't want automatic stub creation you should rename the field so it does not start with `stub`.

Stubs are initialised before each specification, so no state will be maintained from specification to specification (you would have to manually keep state if you require this).

If you need to create stubs that are not fields, you can use the `StubCreator` to do so:

```
import com.googlecode.instinct.actor.StubCreator;

void allowsTheCreationOfStubsThatAreNotFields() {
    SpecificationDoubleCreator creator = new StubCreator();
    Object stub = creator.createDouble(Object.class, "aStubObject");
}
```

Stubs created using Instinct are assigned random values (e.g. for integers and strings), so can be used to avoid needing to triangulate production code. Instinct provides "temporal triangulation", meaning subsequent runs of the specification will provide different values for the stub.

Instinct does not currently auto-create stubs declared in a superclass of a context.

## Mocks ##

**Note. Auto-creation of arrays of arrays of mocks is not available in Instinct 0.1.6.**

Mocks are more advanced stubs, that not only respond to calls made during a test but are also pre-programmed with expectations which form a specification of the calls they are expected to receive. Mocks will throw an exception if they receive a call they weren’t expecting and are checked (called verification) to ensure they received all the calls they expected. Some mocks also verify the order of calls made.

Mocks are created in a specification by marking a field in a context with the `@Mock` annotation or by naming the field with a `mock` prefix (`/^mock.*/`). The following are two examples of a mock.

Using an annotation:

```
@Mock Object object;
```

Using a naming convention:

```
Object mockObject;
```

If you don't want Instinct to automatically create mocks for you, you can turn them off:

```
@Mock(auto = false) Object object = new Object();
```

If the mock field is an array, Instinct will create an array and fill that with mocks of the array's component type:

```
@Mock Object[] objects;
```

If you turn of the automatic creation of a mock, it is still important to mark the role the (mock) object plays in the specification. This helps other developers know the importance of the object in the specification, and whether the interaction of the subject with the mock matters in the current context.

Note that there is no way to disable automatic creation of mocks marked with naming conventions. If you use naming conventions and don't want automatic mock creation you should rename the field so it does not start with `mock`.

Mocks are initialised before each specification, so no state will be maintained from specification to specification (you would have to manually keep state if you require this). Mocks are automatically reset and verified before and after each specification respectively. This removes the need to create infrastructural code that is intermixed with your specifications, clouding their intent.

If you need to create mocks that are not fields, you can use the `Mocker` to do so:

```
import static com.googlecode.instinct.expect.behaviour.Mocker.mock;

void allowsTheCreationOfMocksThatAreNotFields() {
    Object mockObject = mock(Object.class);
}
```

Mocks created this way will still be automatically verified. If you create mocks in another way (such as using jMock's `Mockery`) you will need to manually reset and verify them.

Instinct does not currently auto-create mocks declared in a superclass of a context.


More information on setting expectations on mocks is available on the [expectations page](Expectations.md).

# Fixture #

A fixture is a known set of data (or commands to setup that data) that provide the environment for a set of tests. Fixtures work well when you have a bunch of tests that work on similar data reducing the complexity of your testing environment (fixtures can also have a downside when overused between tests that should have independent data).

Instinct currently has no explicit support for fixtures.

<[User's Guide](UsersGuide.md)>