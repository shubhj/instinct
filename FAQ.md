# Introduction #

# So what is it? #

Instinct is a Behaviour Driven Development (BDD) framework for Java. Inspired by RSpec, Instinct provides flexible annotation of contexts, specifications, actors, etc. (via Java 1.5 annotations, marker interfaces or naming conventions); automatic creation of test doubles (dummies, mocks & stubs) & test subjects; state and behavioural (mocks) expectation API; JUnit test runner integration & Ant tasks.

See the [overview](Overview.md) for more details.

# What! Another testing framework #

While Instinct isn’t intended as a “testing” framework, it can indeed be used as a testing framework in place of JUnit, TestNG, JTiger, etc. Instinct was developed to be used in performing behaviour driven development, which has a slightly different emphasis than testing, focusing more on specifying behaviour and exploring the design of code. We won’t go into the [psychology of why names are important](http://behaviour-driven.org/GettingTheWordsRight), suffice to say that a new framework allows me to explore behaviour driven development (which the projects I’ve been working on have been doing for a while) while also offering additional features not found in current testing frameworks.

Also, Instinct was developed to overcome some deficiencies in current frameworks and provide a simplification of key ideas such as mocking. Instinct also aims to standardise on names of common items used in testing (subject, fixture, mock, stub, dummy, etc.), and provide framework level support for these items.

Instinct will also offer flexibility in the way things are marked, and hence made available to the framework. [TestNG](http://testng.org/) pioneered the use of annotations for marking tests and providing metadata (such as test groups), however there are times when you may not want to use annotations and would prefer say a naming convention (cf. JUnit picks up method names starting with “test”) or a marker interface.

Here’s a complete list of why Instinct was created:

  * Instinct is BDD framework, so has a slightly different focus to conventional testing frameworks.
  * It formalises definitions (by including them in the syntax of the framework) of common test objects such as subjects, mocks & stubs.
  * It Includes test objects directly into the lifecycle of a specification.
  * It does away with needless infrastructure setup such as stub/mock creation.
  * Flexible marking of test objects - specifications, mocks, stubs, dummies, etc. based on annotations, marker interfaces and naming conventions.
  * Test objects are explicitly marked with their function.
  * Simplification of mocks and controllers, the mocking API only provides access to the mock, doing away with the need to access the controller and manage two objects.
  * It removes the need for concrete class inheritance (which most testing frameworks now support anyway).
  * It makes use of Java 1.5 features in order to simplify testing, such as annotations and typesafe mock creation.
  * It embodies lots of common code usually created on TDD projects as Open Source Software making it available outside individual projects.
  * Other Java BDD frameworks (such as jBehave) have a different focus.

# But if I wanted BDD, I’d use jBehave #

True, jBehave is the original BDD framework. However, jBehave's goals and implementation is different to Instinct. jBehave appears to be aiming at a higher level (i.e. not atomic/unit), allowing you to specify acceptance/functional type tests in textual form, and parsing them into executable statements. Also, when Instinct was created jBehave appeared to be an orphaned project, however it has since released a 1.0 version and appears to be thriving.

# Supported JDKs #

Instinct is written and targeted at JDK 1.5. It also works under Java 1.6.

# Dependencies #

Up to date dependencies can be found in the [README](http://instinct.googlecode.com/svn/trunk/core/README).

# Related Projects #

Instinct is not the only BDD framework, others include:

  * Java - [JBehave](http://jbehave.org/), [JDave](http://www.laughingpanda.org/projects/jdave/).
  * .Net - [NSpec](http://nspec.tigris.org/) (the original idea of a DSL for state-based expectations comes from NSpec)
  * Ruby - [RSpec](http://rspec.rubyforge.org/), [test/spec](http://chneukirchen.org/blog/archive/2006/10/announcing-test-spec-0-2-a-bdd-interface-for-test-unit.html)

# Why does Instinct use jMock and not EasyMock? #

Rather than inventing one from scratch, Instinct bundles a mocking library called [jMock](http://www.jmock.org/). jMock was chosen for the following reasons:

  * jMock expectations can be loosened up more than EasyMock. For example consider expecting that the test subject calls either `String.replace()` or `String.replaceAll()` (but not both).
  * The EDSL nature of jMock makes it clear that you're not calling code on the real class but instead writing expectations.
  * It's record/replay mode adds an extra line to every test.

However, EasyMock does offer the following advantages;

  * It is handy in IDEs with method completion on mocks.
  * It is refactoring safe.
  * EasyMock 2.2 has better support for mocking classes than jMock 1.1.

jMock 2 alleviates even more of EasyMock's advantages by offering type inference using an EDSL style.

# Why does Instinct not support test failure and error like JUnit? #

Instinct made an explicit choice to simplify this part of the API and to not distinguish between "failure" and "errror". These two conditions are symptoms of the same cause; a test failure.