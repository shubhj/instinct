# Development tasks by release #

## Release 0.1.7 ##

  * See [Roadmap](Roadmap.md)
  * Cleanup
    * ~~Cleanup boost migrated code~~
      * ~~Generics warnings~~
      * ~~Unused code~~
      * ~~Refactor and merge with main codebase~~
      * ~~Pull over unit tests for Boost code~~
      * ~~Remove JUnit Assert.assertXxx() methods in migrated boost code~~
    * ~~Ensure all classes are policy checked for traits (nulls, etc.).~~
    * ~~Tests for all classes that don't have them, just traits for now.~~
    * Fix all TeamCity inpection warnings and errors.
  * Build
    * ~~Add specs in core to Ant build~~
    * ~~Add specs into AllTestSuite~~
    * ~~Upgrade clover~~
    * Turn checkstyle back on for test code.
    * ~~Automate zipping up of example project~~
  * Refactor
    * ~~Refactor spec methods and specification running~~
    * Cleanup message builders/formatters
      * Consider combining them
      * Make them all use the same formatting code
  * Features
    * Stubs from roadmap.
    * New behaviour expectation API
    * Make command line runner work against directories
    * Add EasyDoc style error capture for aggregating state and behaviour failures
  * Examples
    * ~~Remove Mocker.reset() and Mocker.verify from examples and documentation~~
  * Documentation
  * Development infrastructure
    * New VM for Instinct
    * TeamCity 3
    * Mingle
  * Integration
    * Maven support
    * Make IDEA plugin work again
    * Point IDEA to .classpath instead of IML file for dependency management.
  * Misc
    * Profile Instinct, make it run faster
    * Merge slow and atomic tests

## Release x.x.x ##

  * Re-write all unit tests to be specs


# Previous Releases #

...