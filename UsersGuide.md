# Topics #

  * Introduction
  * [Two minute introduction](InstinctIn2Minutes.md)
  * [GettingStarted](GettingStarted.md)
  * [Obtaining Instinct via Maven](MavenDownload.md)
  * [Creating specifications](Specifications.md)
  * [State and behavioural expectations](Expectations.md)
  * [Creating and using specification actors](Actors.md)
  * [Integrating with JUnit](JUnitIntegration.md)
  * [Integrating with Ant](AntIntegration.md)
  * [Integrating with Clover](CloverIntegration.md)
  * [Terminology](Terminology.md)

# Introduction #

Instinct is a Behaviour Driven Development (BDD) framework for Java. Inspired by RSpec, Instinct provides flexible annotation of contexts, specifications and actors; automatic creation of test doubles and test subjects; a state and
behaviour expectation API; JUnit test runner integration; Ant support and an IntelliJ IDEA plugin.

Instinct is primarily designed to support developers as they are developing code, as an integral part of the act of programming. Instinct specifications also serve as a means of critiquing production code, in a similar manner to more traditional testing frameworks such as JUnit or TestNG.

So how do I get started?

  * [Download it](http://code.google.com/p/instinct/downloads/list)
  * [Use it!](InstinctIn2Minutes.md)
  * [Contribute?](http://groups.google.com/group/instinct-dev)

Can I read more?

  * [Better Testing Through Behaviour](http://adams.id.au/blog/wp-content/uploads/2007/10/OSDC2007BetterTestingThroughBehaviour.pdf), Tom Adams, [OSDC 2007](http://osdc.com.au/) ([slides](http://adams.id.au/blog/wp-content/uploads/2007/10/OSDC2007BetterTestingThroughBehaviourPresentation.pdf)).
  * [Introducing Behaviour-Driven Development](http://dannorth.net/introducing-bdd/), Dan North.
  * [A New Look at Test Driven Development](http://blog.daveastels.com/files/BDD_Intro.pdf), Dave Astels.
  * [Behavior Driven Development: An Evolution in Testing](http://www.agiledenver.org/2007AprMeeting.php), Bob Cotton, Agile Denver, April 2007.

See/hear people talk about it?

  * [Behaviour Driven Development in Ruby with RSpec](http://rubyconf2007.confreaks.com/d3t1p2_rspec.html), Dave Astels & David Chelimsky, [RubyConf 2007](http://rubyconf.org/) ([slides](http://blog.davidchelimsky.net/files/BDDWithRspec.RubyConf.2007.pdf)).
  * [Dave Astel's Beyond Test Driven Development Google Talk](http://video.google.com/videoplay?docid=8135690990081075324)
  * [Dave Astels and Steven Baker on RSpec and Behavior-Driven Development at InfoQ](http://www.infoq.com/interviews/Dave-Astels-and-Steven-Baker)

Find more information?

  * [BDD main page](http://behaviour-driven.org/)
  * [BDD on Wikipedia](http://en.wikipedia.org/wiki/Behavior_driven_development)

# Colophon #

This user's guide gives an introduction to Instinct. The examples for this tutorial come from the [example project](http://instinct.googlecode.com/svn/trunk/example/) (also available from the [downloads page](http://code.google.com/p/instinct/downloads/list)), full details and examples of all Instinct features are available there. The tutorial assumes you have [downloaded](http://code.google.com/p/instinct/downloads/list) Instinct. Details on terminology used are available on the [terminology page](Terminology.md).