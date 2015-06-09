# Road Map by Release #

Other ideas are available on [FeatureIdeas](FeatureIdeas.md).
Current development tasks are available on [DevelopmentTasks](DevelopmentTasks.md).

## Release 0.1.10 ##

  * Core Features
    * Improved stub creation
      * Interfaces, recursive constructor arguments.
      * Stub creation for classes containing non-constructor initialised methods (these may produce NPEs).
    * Specification groups.
    * Allow embedded contexts.
    * Automatic creation of specifications actors for inherited and nested contexts.
    * Automatic creation of subjects.
    * Naming conventions (Default prefi & Impl suffix) for automatic subject creation.
    * Move timing code from CommandLineRunner into BriefResultsFormatter.
  * Expectation API
    * Improve behaviour expectation syntax (see sandbox).
    * Exception checking - See Boost's assertThrows, inc.
    * Trait checking - Serializable, rejects null - expect.that(Foo.class).isSerializable(), expect.that(Foo.class).rejectsNullsInPublicMethods()
    * More functional java matchers
      * Option
      * Either
  * sbt integration
    * [http://code.google.com/p/simple-build-tool/wiki/TestFrameworkExtensions Basic integration
  * Ant integration
    * Add watchdog around forked VM.
    * Intermittent classpath issues.
    * Clean up termination and detection of failures using the failure property.
    * JUnit Integration
      * TestDox output formatter, see JDave for details.
      * RSpec HTML output format.
      * Confirm JUnit runner behaviour in Eclipse (Ctrl-Shift-F10 equivalent).
      * Custom extension to expectation API (via interface composer).
    * Prototype Scala API
    * Misc
      * Checkstyle errors on Windows.
      * [defect-24](http://code.google.com/p/instinct/issues/detail?id=24)

## Release 0.2.0 ##

  * Documentation
    * Document lifecycle - how to extend, how to get callbacks for special behaviour.
  * Build
    * Add simian
    * Self host all current tests.
    * Test coverage to 100%
    * Fail release build if any fixes left for that release.
  * Infrastructure
    * Rationalise JAR packaging (single, multiple?).
  * Ant integration - InstinctAntTask
    * Allow multiple formatters
    * Add XML file reporting in JUnit task format.
  * Ant integration - InstinctReportAntTask
    * Create HTML specification reports from XML results

## Release 0.2.1 ##

  * IntelliJ Plugin
    * Inspections/intentions
      * Add generators for contexts & lifecycle methods.
      * Lookup based on subject annotation/naming convention.
      * Add intention/inspection for areas where the expectations could be improved. eg. expect.that(container.size()).equalTo(3) => expect.that(container).isOfSize(3)
      * Hook into "Create field ..." intention to annotate fields as dummies, mocks, etc.
      * Allow loosening/tightening of mock constraints, one(foo).bar(bar) => one(foo).bar(with(eq(bar))), tab between constraints (with list of eq(), same(), etc. Also work on returnValue, throwsException, etc.
    * Runer
      * Fix current intermittent errors when running specs.
      * Hook into JUnit runner for GUI.
    * Release on IDEA plugins site.
    * Add a view of pending specifications

# Previous Releases #

## Release 0.1.9 ##

  * ~~Maven integration~~
    * ~~Initial Maven artefacts available on main repo.~~
  * ~~JUnit Integration~~
    * ~~JUnit XML test output formatters, for integration of JUnit style XSL stylesheets.~~
  * ~~Prototype Scala API~~

## Release 0.1.7 ##

  * Core Features
    * ~~Marking of double fields based on naming convention.~~
    * ~~Auto-create arrays of test doubles.~~
  * Expectation API
    * ~~Add isEmpty() and isNotEmpty() to iterable checker.~~
  * JUnit integration
    * ~~Shipping with `junit-dep-4.4.jar` to avoid bundled Hamcrest.~~
  * Bugs
    * ~~(defect-33) CollectionChecker.hasTheSameContentAs() is broken~~

## Release 0.1.6 ##

  * Core Features
    * ~~Remove the need for @Context annotation.~~
    * ~~Automatic creation of specification doubles: mocks, stubs and dummies~~.
    * ~~Automatic reset and verification of mocks.~~
    * ~~@BeforeSpecification, @AfterSpecification, @Specifications (and naming convention equivalents) can be used across base and subclasses.~~
    * ~~Better error messages for hasBeanProperty and hasBeanPropertyWithValue.~~
  * Expectation API
    * ~~Make expectations more like natural language. eg. isEqualTo(), doesNotEqual(), etc. Existing code using equalTo(), etc. will need to be updated.~~
    * ~~Collection checkers: hasTheSameContentAs(Collection~~

&lt;E&gt;

) and hasTheSameContentAs(E...). These only check content and not the order of elements.~~*~~Ensure all "collection" classes (Array, Map, Set, List, String, SharSequence) have similar size checkers available.~~*~~Added file checker,~~* JUnit integration
    *~~ Fix Eclipse [unrooted context](http://code.google.com/p/instinct/issues/detail?id=20).~~* Ant integration
    *~~Support for custom classpath.~~*~~Quiet specification result formatting (only shows errors and pending specs).~~*~~Use correct project logging level for errors, etc.~~* jMock integration
    *~~Support states: `Mockery.states(String)`.~~* Infrastructure
    *~~Removed reliance on Boost, transferred all relevant Boost classes locally.~~*~~jMock 2.4.~~*~~Downgraded to CGLib 2.1.3 (for Maven integration).~~* Bugs
    *~~Miscellaneous NullPointerExceptions and null related problems in state expectation API.~~*~~(defect-3) IterableChecker should have a containsOnly method or something.~~*~~(defect-8) @BeforeSpecification does not run if implemented in an abstract base class.~~*~~(defect-20) CEclipse Junit4 InstinctRunner shows tests under the "Unrooted Tests" node. ~~*~~(defect-22) Context treeview shows baseclass and subclass when only subclass is run~~*~~(defect-23) Overridden specifications run twice.

## Release 0.1.5 ##

  * Core Features
    * ~~Initial cut of pending specifications~~
  * Expectation API
    * ~~Behaviour expectation API.~~
    * ~~Move old mocking code to jMock 2, remove jMock 1.2 dependency.~~
    * ~~Add regular expression checking to string checker.~~
    * ~~Add empty checks to array checker.~~
    * ~~Expected exception (simple version, encoded in specification annotation)~~
  * JUnit Integration
    * ~~Fix concurrent modification error in JUnit 3 code.~~
    * ~~JUnit 4 runner.~~
  * QuickCheck Properties (ala Popper Theories)
    * ~~Initial prototype API~~
  * Bugs
    * ~~Remove state-based expectation API null checks.~~
    * ~~Clean up generic warnings.~~