<[User's Guide](UsersGuide.md)>

# Terms #

This page lists the terminology used in Instinct.

## Specification ##

A method in which the behaviour of a piece of code is specified. Specifications are executable examples that guide the design process and provide both documentation and tests. Specifications (specs) can be thought of as test methods in XUnit frameworks.

## Context ##

A context in which a certain behaviour is valid. Contexts can be used to group specifications together and set up state in which a number of specs are valid.

## Actor ##

An actor in a specification. Subjects, dummies, stubs and mocks are all actors.

## Subject ##

The class whose behaviour is under scrutiny.

## Test Double ##

An implementation of an interface (or extension of a class) that is only used for testing. Doubles can be manually created (plain classes that implement an interface) or auto-created via a framework (e.g. JMock & EasyMock).

## Dummy ##

Dummy objects are passed around but never actually used, they are usually used just to fill parameters to simplify specs (where their behaviour may not be important). Dummies will throw exceptions if methods are called on them. They are the simplest form of test double implementation.

## Stub ##

Stubs respond to method calls made during a test by providing canned answers. Stubs may also record information about calls, such as an email gateway stub that remembers the messages it 'sent', or maybe only how many messages it 'sent'.

Stubs can be manually created or automatically created by a framework, e.g. [EasyMock's Nice Mocks](http://www.easymock.org/EasyMock2_2_Documentation.html).

## Mock ##

Mocks are more advanced stubs, that not only respond to calls made during a test but are also pre-programmed with expectations which form a specification of the calls they are expected to receive. Mocks will throw an exception if they receive a call they weren't expecting and are checked (called verification) to ensure they received all the calls they expected. Some mocks also verify the order of calls made.

## Fixture ##

A fixture is a known set of data (or commands to setup that data) that provides the environment for a set of tests. Fixtures are really nice when you have a bunch of tests that work on similar data reducing the complexity of your testing environment (fixtures can also have a downside when overused between tests that should have independent data).

## Auto Mocking ##

The process by which test doubles are automatically created by Instinct. Also known as auto test double creation.

# References #

  * [TestDouble](http://martinfowler.com/bliki/TestDouble.html)
  * [xUnit Patterns](http://xunitpatterns.com/Book%20Outline.html)
  * [Rails Test Fixtures](http://manuals.rubyonrails.com/read/chapter/26)
  * [SPUnit Cookbook](http://spunit.sourceforge.net/Cookbook.html)
  * [RSpec](http://rspec.rubyforge.org/)

<[User's Guide](UsersGuide.md)>