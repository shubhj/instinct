<[User's Guide](UsersGuide.md)>

# Expectations #

Many testing and BDD frameworks provide a means of making assertions about the state of objects under test (the subject), or objects that are returned by the subject, these APIs are typically called assertion APIs. Mocking frameworks allow expectations to be made on mocks, specifying calls that another class is expected to make on the mock. Instinct takes the view that these two kinds of functions are really just different angles on the same thing. Assertion APIs state expectations about an object's state, mocking APIs state expectations about an object's behaviour (these are often called state & interaction testing respectively).

Instinct contains a single API for stating expectations, the expectation API. Instinct uses [Hamcrest](http://code.google.com/p/hamcrest/) for its state expectations and [jMock](http://jmock.org/) for its behaviour expectations. Instinct's expectation API uses a domain specific language (DSL) or fluent style of API to allow you to create readable expectations. The entry point into the expectation API is the  `Expect` class, which contains a single public static field:

```
public final class Expect {
    public static final ExpectThat expect = new ExpectThatImpl();
    ...
}
```

While you can create an instance of `ExpectThat` in your specifications (say as a field or local variable), it is designed to be statically imported as follows:

```
import static com.googlecode.instinct.expect.Expect.expect;

class AnEmptyStack {
    ...
    @Specification
    void isEmpty() {
        expect.that(stack.isEmpty()).isTrue();
    }
    ...
}
```

Statically importing the `expect` field allows the expectations to read naturally: "expect that stack isEmpty is true".

# State expectations #

The instinct state-based expectation API is built on top of the Hamcrest Matcher API. The Instinct wrapper methods enable:

  * IDE code completion, reduced to the methods that apply to the type object being checked.
  * A more readable, plain English like (and consequently longer) flow, within the constraints of Java's syntax and type system.
  * Standard Hamcrest Matchers (or any custom Matcher implementations) can be supplied to extend the capabilities of the expectation API.

Expectations should clearly denote the intent of the code they are specifying. Consider the following JUnit assertion:

```
void testRunnerSendsSpecifiationResultsToOutput() {
  assertTrue("Expected to find context name", runnerOutput.contains(className));
}
```

And the corresponding Instinct expectation:

```
void sendsSpecifiationResultsToOutput() {
  expect.that(runnerOutput).containsString(className);
}
```

The Instinct expectation clearly denotes the intent of the statement. Because of this, Instinct expectations do not need to include strings describing the condition being checked, as the expectation itself clearly denotes the intent. Consider the following JUnit  assertions:

```
assertEquals(1, map.size());
assertTrue(map.containsKey(1000));
assertEquals(fileName, map.get(1000));
```

These can be re-written as:

```
expect.that(map).hasSize(1);
expect.that(map).containsKey(1000);
expect.that(map).containsEntry(1000, fileName);
```

When using the expectation API you not only get better syntax, the process of creating the expectations is much easier. Use of the expectation API has been specifically tailored to guide you down the right path as you're typing the expectation. You don't need to remember which checks are applicable to which type, the API takes care of that (unless you want specific functionality not provided by Instinct). It does this by using type-safe checkers to ensure that you can only set expectations for the type of the object you are checking, exploiting the completion features of Java IDEs. For example it makes sense to check the length of an array or a list, but not an integer.

Consider the following subject.

```
interface FileChunkHolder {
    Map<Integer, String> getFileNamesByChunk();
}
```

Here's a specification that describes a portion of its behaviour.

```
class AFileChunkHolderWithASingleChunkFile {
    @Subject FileChunkHolder fileChunkHolder;
    @Stub String fileName;

    ...

    @Specification
    void returnsTheEntryForASingleChunk() {
        Map<Integer, String> chunkFiles = fileChunkHolder.getFileNamesByChunk();
        expect.that(chunkFiles).hasSize(1);
        expect.that(chunkFiles).containsEntry(1000, fileName);
    }
}
```

The checks that Instinct makes available at the end of typing "`expect.that(chunkFiles).`" are only applicable to maps.

Instinct provides a thin wrapper around the standard Hamcrest matchers so that they are integrated into the common expectation API and provide type-safe matching. For example the API will only show you the matchers that are appropriate to the type you are checking. Instinct also uses this wrapping to provide more readable method names. For example the `IsEqual.equalTo()` matcher is used in Hamcrest as `assertThat(foo, equalTo(bar))`, whereas in Instinct it reads more naturally as `expect.that(foo).isEqualTo(bar)`. You don't need to remember which matchers are applicable to which types, the expectation API will guide you to the correct ones.

To use the Instinct checkers, you must first statically import the `Expect.expect` field, then, simply pass in the value you're interested in checking into the `that()` method (note that following expectation does not actually do anything):

```
Object myValue = new Object();
expect.that(myValue);
```

After typing this, place a dot after the last method call, and let your IDE offer expectations that are valid for the type of the value passed (some IDEs will automatically do this, others will require you to press a key such as `Ctrl-Space`). Here is a screenshot of this in IntelliJ IDEA.

![http://instinct.googlecode.com/svn/wiki/images/StateExpectationsCompletion.png](http://instinct.googlecode.com/svn/wiki/images/StateExpectationsCompletion.png)

If something does not match your expectations, you get a clear indication of why it failed.

```
java.lang.AssertionError: 
Expected: a value greater than or equal to <3>
     got: <1>
```

The Instinct state-based expectation API will fail fast, like traditional assertion APIs. For example if a value does not meet the first expectation in group of expectations, you will only be notified of the first failure, even though other expectations may not be met. This is in contrast to the behaviour API, which records all expectation failures and reports them at once (this is jMock's default behaviour). This can sometimes be confusing when intermixing state and behaviour expectations, if you do not drive out behaviour in small enough increments.

Consider the following specification, whose subject has not yet been implemented, so will not meet the behaviour expectations specified:

```
expect.that(new Expectations(){{
    one(collaborator).doStuff(); will(returnValue(true));
}});
expect.that(subject.didStuff()).isEqualTo(true);
```

As the verification of the behaviour occurs after the specification method completes, you will get notified of the state expectation failure even though it was the interaction of the subject with its collaborator that did not occur. Future versions of Instinct will address this, and report all state expectation failures alongside behaviour expectation failures.

There are downsides to using the inbuilt in Instinct state-based checkers. While the default checks can be extended by using custom Hamcrest matchers (see later sections), they do not integrate into the DSL of the expectation API, so become in effect second-class citizens. This is a compromise Instinct is willing to make, as the majority of the time you get a much nicer API to work with, and still have the ability to extend the framework if needed.

Future versions of Instinct will make it easier to provide your own extensions to the expectation API, by directly grafting on new functionality to the expectation DSL (`ExpectThat`). For those interested, the mechanism is available in the instinct `sandbox` package, and consists of providing dynamic interface composition, implemented in a similar manner to "duck typing" method dispatch in dynamic languages such as Ruby.

## Checkers ##

The Instinct expectation API supports the following state-based checkers.

### Array Checker ###

The array checker allows you to check the state of objects in an array. It supports expectations about objects, container classes (arrays, collections & iterables) and classes that have the notion of size (arrays, collections, maps & strings).

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutArrays() {
    final String[] greetings = {"hi", "there"};
    expect.that(greetings).isOfSize(2);
    expect.that(greetings).containsItem("hi");
    expect.that(greetings).doesNotContainItem("bye");
    expect.that(greetings).doesNotContainItem(greaterThan("zip"));
}
```

### Boolean Checker ###

The boolean checker allows you to check the state of booleans. It supports expectations about objects, booleans and comparable classes.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutBooleans() {
    expect.that(true).isTrue();
    expect.that(false).isFalse();
    expect.that(true).isGreaterThan(false);
    expect.that(false).isLessThanOrEqualTo(false);        
}
```

### Class Checker ###

The class checker allows you to check the state of classes. It supports expectations about objects and classes.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutClasses() {
    expect.that(String.class).isTypeCompatibleWith(Comparable.class);
    expect.that(Comparable.class).isNotTypeCompatibleWith(String.class);
}
```

### Collection Checker ###

The collection checker allows you to check the state of objects in an collection. It supports expectations about objects, container classes (arrays, collections & iterables) and classes that have the notion of size (arrays, collections, maps & strings).

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutCollections() {
    final List<String> people = new ArrayList<String>();
    expect.that(people).isEmpty();
    people.add("fred");
    people.add("wilma");
    expect.that(people).isNotEmpty();
    expect.that(people).isOfSize(2);
    expect.that(people).containsItems("fred", "wilma");
    expect.that(people).containsItem("fred");
    expect.that(people).doesNotContainItems("barney", "betty");
    expect.that(people).hasTheSameContentAs("fred", "wilma");
    expect.that(people).hasTheSameContentAs(asList("fred", "wilma"));
}
```

### Comparable Checker ###

The comparable checker allows you to check the state of comparable objects. It supports expectations about objects that implement the `java.lang.Comaprable<T>` interface.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutComparables() {
    expect.that(1).isEqualTo(1);
    expect.that(1).isGreaterThan(0);
    expect.that(1).isGreaterThanOrEqualTo(1);
    expect.that(1).isLessThan(2);
    expect.that(1).isLessThanOrEqualTo(2);
}
```

### Double Checker ###

The doubles checker allows you to check the state of doubles. It supports expectations about objects, doubles and comparables.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutDoubles() {
    expect.that(1.1).isCloseTo(1.0, 0.11);
    expect.that(1.1).isNotCloseTo(1.0, 0.1);
}
```

### EventObject Checker ###

The event object checker allows you to check the state of EventObjects. It supports expectations about objects and event objects.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutEvents() {
    final Object object = new Object();
    final EventObject myEventObject = new MyEventObject(object);
    expect.that(myEventObject).isAnEventFrom(MyEventObject.class, object);
    expect.that(myEventObject).isAnEventFrom(object);
    expect.that(myEventObject).isNotAnEventFrom(new Object());
}
```

### File Checker ###

The file checker allows you to check the state of files. It supports expectations about objects and file objects.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutFiles() {
    final File javaHomeDirectory = new File(getProperty("java.home"));
    expect.that(javaHomeDirectory).exists();
    final File nonExistentDirectory = new File("foo/bar/baz");
    expect.that(nonExistentDirectory).doesNotExist();
}
```

### Iterable Checker ###

The iterable checker allows you to check the state of iterable objects. It supports expectations about objects, iterables and classes that have the notion of size (arrays, collections, maps & strings).

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutIterables() {
    final Iterable<String> people = asList("fred", "wilma");
    expect.that(people).containsItems("fred", "wilma");
    expect.that(people).containsItem("fred");
    expect.that(people).doesNotContainItems("barney", "betty");
}
```

### Map Checker ###

The map checker allows you to check the state of maps. It supports expectations about objects, maps and classes that have the notion of size (arrays, collections, maps & strings).

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutMaps() {
    final Map<String, String> map = new HashMap<String, String>();
    expect.that(map).isEmpty();
    map.put("key", "value");
    expect.that(map).isNotEmpty();
    expect.that(map).isOfSize(1);
    expect.that(map).containsKey("key");
    expect.that(map).containsValue("value");
    expect.that(map).containsEntry("key", "value");
}
```

### Node Checker ###

TODO

### Object Checker ###

The object checker allows you to check the state of objects. It supports expectations about objects.

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutObjects() {
    expect.that("fred").isEqualTo("fred");
    expect.that("fred").isNotEqualTo("barney");
    expect.that(this).isTheSameInstanceAs(this);
    expect.that("fred").isNotTheSameInstanceAs("barney");
    expect.that(this).isOfType(StateExpectationsExample.class);
    expect.that(this).isNotNull();
    expect.that("fred").matches(startsWith("fr"), containsString("ed"));
    expect.that("fred", equalTo("fred"));
}
```

### String Checker ###

The string checker allows you to check the state of strings. It supports expectations about objects, strings and classes that have the notion of size (arrays, collections, maps & strings).

The following is an example:

```
@Specification
public void providesMatchersForMakingAssertionsAboutStrings() {
    expect.that("andersdabeerz").isEqualToIgnoringCase("AndersDaBeerz");
    expect.that("andersdabeerz").startsWith("anders");
    expect.that("andersdabeerz").containsString("da");
    expect.that("andersdabeerz").endsWith("beerz");
    expect.that("andersdabeerz").isEqualToIgnoringWhiteSpace(" andersdabeerz ");
    expect.that("andersdabeerz").doesNotContainString("water");
}
```

## Adding new checkers and matchers ##

The state-based expectation API can be extended...

TODO: Describe the extensions mechanisms available.

  * Inherit from/delegate to the checkers
  * Additional hamcrest matchers
  * Create your own Expect and ExpectThat implementation.

# Behaviour expectations #

Instinct's mocking is based on [jMock](http://www.jmock.org/), with thin wrappers that make jMock expectations look more like Instinct's state expectations and that simplify (i.e. hide) the lifecycle of jMock `Mock`s and `Mockery`.

As such you can use make use of all of [jMock's syntax](http://www.jmock.org/cheat-sheet.html) when writing your specifications:

```
class ACsvFileReaderWithNothingToRead {
    @Subject private CsvFileReader csvFileReader;
    @Mock private CsvFile csvFile;
    @Stub(auto = false) private CsvLine[] noLines;

    @BeforeSpecification
    void before() {
        noLines = new CsvLine[]{};
        csvFileReader = new CsvFileReaderImpl(csvFile);
    }

    @Specification
    void returnsNoLines() {
        expect.that(new Expectations() {{
            one(csvFile).hasMoreLines(); will(returnValue(false));
            ignoring(csvFile).close();
        }});
        expect.that(csvFileReader.readLines()).isEqualTo(noLines);
    }
}
```

TODO: Discuss:
  * jMock sequences
  * jMock State machines

<[User's Guide](UsersGuide.md)>