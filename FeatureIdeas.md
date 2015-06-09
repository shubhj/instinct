# Feature ideas #

## Markers ##

How do you find things? Specifications, contexts, mocks, etc.

  * ~~Naming conventions~~
  * Marker interfaces
  * ~~Annotations~~
  * Method signatures - "public void ...()" for classes in the test tree for example. May need classes to be marked as contexts.
  * Define naming conventions etc. in properties file (mappings between interface names and implementations for test subjects).
  * Remove duplicates found in locators, e.g. fields marked with annotation and naming convention.
  * Add @Provided or @Wired to have a field resolved using an IoC container.
  * @Fixtures have a lifecycle - init() & destroy() - maybe these are annotations & naming conventions also.
  * When finding fixtures find all fields which implement marker interfaces.
  * Ensure that methods that are marked as both Before and After fail (are there any other mutually exclusive methods?)

### Annotations ###

  * ~~Use @BehaviourContext annotation to denote context classes.~~
  * Include an @Fixture annotation to support auto creation of fixtures where able to.
    * If fixtures depend on mocks, use the mock types where possible
    * Perhaps have conditions places on the fixtures to denote how they should be created @Fixture(dependencies=AUTO\_MOCK,AUTO\_UNIQUE)
  * ~~Do we even need behaviour context markers? Surely we can just find the classes containing specifications and execute them (with currently implemented checks)~~
  * Add a "description" attribute to the BehaviourContext annotation, to give a description to use when reporting (errors, reports, documentation, etc.)

### Naming Conventions ###

  * ~~Unique fields (also want to be able to specify conditions, e.g. positive int, ranges, etc.)~~
  * ~~Mocked fields (a mock that is verified)~~
  * ~~Stub fields (just an instance)~~
  * Fixtures

## Integration ##

  * IntelliJ IDEA plugin
  * Eclipse plugin?
  * ~~Ant support~~
  * Maven plugin (or SureFire integration)
  * ~~Support for JUnit runners.~~
  * Scala
    * Ideas on how to provide RSpec like syntax: http://villane.wordpress.com/2008/02/02/learning-scala-performance-impact-of-implicit-conversions/

### IntelliJ IDEA Plugin ###

  * Add syntax highlighting of mocks, etc., remove built in warnings for fields that haven't been assigned a value.
  * Attempt to re-use JUnit plugin in IDE.
  * ~~Look at JBehave IDEA plugin.~~
  * Intentions
    * x == y -> expect.that(x).sameInstance(y)
    * x.equals(y) -> expect.that(x).equals(y)
    * foo.bar() -> expect.that(foo).method("bar");
  * Create fields, when declaring a subject (see Rob's plugin). Fields marked as mocks, stubs, etc.

## Testing ##

  * Allow/encourage more than one Context per class file
  * Provide UniqueTestUtil functionality, @Unique annotation & naming convention (are these just dummies?)
  * ~~Create a Verify class similar to Assert. This will allow static access to the checks (so that this just becomes a wrapper). Probably use [Hamcrest](http://code.google.com/p/hamcrest/) to do this.~~
  * ~~Context classes should not need to have a suffix "Context".~~
  * Provide closure style assertThrows checks.
  * Support grouping of contexts
    * Via annotation
    * Via marker interfaces, allow extension of base marker interfaces to define new groups
    * Add this into Ant support as option on aggregators (i.e. name of group to run)
    * Add this into IntelliJ plugin, perhaps as an option in run dialog
    * Does it make sense to add this to the Context annotation or specification annotation like TestNG?

## Mocking ##

  * ~~Bundle a mocking library - probably JMock~~
  * ~~Create automocker - dummies, stubs & mocks~~
    * ~~Support automocking using an annotation (@Mock) and naming convention~~
    * ~~Fields for automocking will require annotation or naming convention (& currently hold null value?)~~
  * ~~Mock role names should be discovered by using the name of the field (in addition to standard JMock method).~~
  * ~~Possibly allow different mock types - nice, stubs, etc.~~
  * Should allow mocking to be done automatically or manually. Manual mocking should be explicitly allowed.
  * ~~Expose Mocker via static utility, should have the same hooks into the lifecycle (e.g. verification) as auto mocked fields.~~
  * Implementations ideas:
    * Use JMock style DSL. Wrap JMock classes thinly.
    * Perhaps allow plugable mocking implementations?
    * See [this](http://shareandenjoy.saff.net/2006/12/interface-imposterization.html) for details on how jMock 2 does it.
  * Propagate mock verification errors on assertion failures (need to call Mocker.verify()).
  * Check mocking of toString() for classes that override Object.toString() (apparently a jMock 1.1 bug).
  * Collection all verification errors and print them as one, rather than bailing on first, report them as one.
  * On expectation failre, dump out values of dummy variables/fields (the context).
  * On expectation failure, dump out context of expected and actual.
  * Replace EasyMock class extensions with Objenisis.
  * ~~When creating concrete stubs, for string fields add the name of the field into the unique string.~~
  * Things to be auto wired, test actors/fixtures
    * ~~Mocks~~
    * Instance provided fields.
    * Integration tests: Objects resolved through wiring (Spring, Guice, etc.). See ttp://www.amazon.com/Xbrand-XB-1002-Height-Adjustable-Laptop/dp/B000GB3E9W
    * Integration tests: Things that are added to the wiring (on demand) so that other components will use a mock/stub/etc.
  * Partial mocks/stubs? Use a concrete class (proxy it), allow the mocking/stubbing out of certain methods. Could be used to simply mock out a certain method (come up with a mocha like syntax), or, only to verify that certain methods are called.
  * Stubbing strategy (which is the default)
    * Return null from all non-void methods (see Mocha stub\_everything).
    * Proxy the class, use instance provider to return instances for non-void methods.
    * Create a concrete instance of the class (use instance provider).
    * Provide returns inline (ala mocha) - fail if other methods called?
    * Allow custom strategies to be plugged in?

## Aggregation ##

  * ~~Hook into junit style runners for IDE integration~~
  * IntelliJ plugin to run tests individually (look at code from TestNG plugin - http://code.google.com/p/testng-idea/).
  * Add ordering to mock API.

## Functionality ##

  * Allow "naming conventions" to be added (discover these at runtime to extend functionality).
  * Need some way to discover what methods to run for non-annotated classes. Provide a naming convention to do this.
  * ~~For classes with annotations, may not want to also add in specification methods discovered by naming conventions.~~

## Test Strategy ##

  * Determine how to automatically test core code against example code (maybe seperate classloader). This could form a  "compatibility" suite.

## Implementation Details ##

  * ~~Do we need to create a new instance of the context class for each specification method?~~
  * ~~Remove the need for marking classes with a context. How do we then handle contexts that have inherited spec methods? If we find classes to run based on spec methods we shouldn't run parent classes also. Perhaps we don't run abstract classes? Keep a list? Does this apply only to aggregators? Does it matter?~~
  * Create a way to dynamically compose Expect implementations, and how they releate to checkers. So that callers can create expectation instances in code (see David Saff's comment in mailing list).

## Lifecycle ##

  * Expose lifecycle (in specification runner) as an annotation on the context (like JUnit's RunWith). Use the default lifecycle unless overridden
  * Provide a callback (listener/handler) interface so that a class can register to be notified of lifecycle events. This would allow extension to enable features like those used in EasyDoc test code.

## Documentation ##

  * Write example using: [One Expectation per Example: A Remake of "One Assertion Per Test"](http://www.daveastels.com/articles/2006/08/26/one-expectation-per-example-a-remake-of-one-assertion-per-test)
  * ~~Document automocking capabilities:~~
    * ~~Declaring an array or collection will fill that collection with mocks.~~

## Ideas ##

  * Jmockit
  * VirtualMock