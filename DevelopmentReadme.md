# Important URLs #

  * [Instinct homepage](http://instinct.googlecode.com/)
  * [TeamCity](https://build.workingmouse.com/) - The build server. Contact the developers list for authentication details.

# Java version #

Instinct is built using Java 1.5. The build server (TeamCity) runs this against this version, so at worst all release builds will built using 1.5. That said, there is nothing stopping you from developing on 1.6 so long as you don't build in any 1.6 specific code.

If you do develop on 1.6, you should build against 1.5 before copying the Instinct JAR into the examples project, otherwise the build server will complain about an incorrect Java version.

# Modules #

Instinct is composed of several modules:

  * **core** - The core of Instinct.
  * **example** - An example project using Instinct. Contains examples of all Instinct features.
  * **idea** - IntelliJ IDEA plugin.
  * **eclipse** - The Eclipse plugin.

Each of these is physically located in the top level of a checkout, the `instinct` directory in the examples below.

If you just want to do work on Instinct, the `core` module is what you probably want.

# Quick development setup #

  1. Configure IDE as described below.
  1. Build the core.

# IDE Configuration #

## IntelliJ IDEA 7 ##

  1. Add the Copyright plugin. The copyright should be picked up automatically (it's stored in the module file). If not, you can import the settings from either of the project files. Alternatively, add in the licence (see an existing source file) and set the name to your name (it defaults to "Workingmouse").
  1. Open the IDEA project; instinct-core.ipr for core & example development, instinct-idea.ipr for plugin development.
    1. If you get errors asking for the `TEMPLATE` variable, set it to anything (the copyright plugin seems to want it sometimes).
  1. Import the IDE settings jar (File -> Import Settings...) from `core/etc/idea-settings.jar`.
    1. Be sure to select the "Instinct" inspection profile. If you do this correctly you'll get the text "Instinct" beside the little inspector icon in the bottom right of the status bar (as opposed to "Default" or "Project").
    1. If you're on a Mac, be sure to deselect the keyboard shortcuts so you don't get Linux/Windows bindings.
    1. Select the "Global" code style.
  1. In the project's compiler options, set the following:
    1. Enable "Clear output directory on rebuild".
    1. Enable "Compile in background".
    1. Enable 'Honour dependencies in "Compile" command'.
    1. Disable 'Add @NotNull" annotations'.
  1. In the project's compiler options, set the "Resource patterns" to the following: `?*.properties;?*.xml;?*.gif;?*.png;?*.jpeg;?*.jpg;?*.html;?*.dtd;?*.tld;?*.csv`

If you make changes to inspection profiles, be sure to discuss them on the list, and then export them over the top of the existing settings. You should export the following settings:

![http://instinct.googlecode.com/svn/wiki/images/InstinctIDEAExportSettings.png](http://instinct.googlecode.com/svn/wiki/images/InstinctIDEAExportSettings.png)

## Eclipse 3.x ##

  * EclipseConfiguration

# Builds #

To build from source, Instinct requires Java 1.5, Ant 1.7.0 and JUnit 4.4.

Shell scripts for Unix & Windows systems are provided in the project's root
directory. These do not require any other dependencies and can be run standalone.

To use a default Ant installation, you will need to add the JUnit jar to
ANT\_HOME/lib directory.

The shell scripts were written for Unix-based machines and as such will work fine on Mac & Linux boxed. If you are on Windows, you may encounter some grief, e.g. the end of line markers cause checkstyle failures, this is a known issue.

## Core ##

The Instinct core contains only a single build, there is no distinction between a "release" build vs. a "commit" build.

The core can be built as follows:

```
$ cd instinct/core
$ ./build.sh
```

## Example ##

The example project can be built as follows:

```
$ cd instinct/example
$ ./build.sh
```

The examples project contains a static version of the Instinct Jar file (as it's intended to be stand-alone). If you add new examples, you may need to copy the built Jar file from the core into the examples project (you will need to build Instinct with Java 1.5):

```
$ cp core/build/instinct-x.x.x.jar example/lib/instinct-x.x.x.jar
```

The examples project is used for documentation, so ensure that for new features you add you also create examples of their usage.

## IDEA ##

The IntelliJ IDEA plugin does not currently have a build, it is built from within IDEA. The plugin will currently only build under IntelliJ IDEA 6.x, and as such you may need to exclude it from IntelliJ compilation.

# Code Guidelines #

As far as possible, tools have been added to simplify the writing of consistent code. For example checkstyle in the Ant build will catch some formatting errors. Formatting of code is encompassed in the IntelliJ setings, you should adhere to these formatting options.

Here is an example class with acceptable formatting:

```
/*
 * Copyright 2006-2007 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.internal.locate.field;

import com.googlecode.instinct.marker.naming.NamingConvention;
import java.lang.reflect.Field;
import static java.util.Collections.unmodifiableSet;
import java.util.Set;
import java.util.TreeSet;

public final class NamedFieldLocatorImpl implements NamedFieldLocator {
    public <T> Set<Field> locate(final Class<T> cls, final NamingConvention namingConvention) {
        final Set<Field> fields = new TreeSet<Field>();
        for (final Field field : cls.getDeclaredFields()) {
            if (field.getName().matches(namingConvention.getPattern())) {
                fields.add(field);
            }
        }
        return unmodifiableSet(fields);
    }
}
```

Some notes:

  * Note the lack of whitespace. If you need whitespace, you usually need a method(s).
  * Single lines under the class declaration are OK if there's only methods, if there are fields, remove the line.
  * No star (`*`) imports, use explicit imports. Imports are in alphabetical order.
  * Opening curly braces ({) go on same line.

## Production code ##

  * User-facing code goes into top level packages, internal code belongs in internal. It's OK if external code returns internal implementations. Prefer to return external interfaces though.
  * There is no IoC container, play around if you want to use constructor injection, or use manual field injection. Avoid setter injection.
  * Prefer interfaces for all classes. "Data" container type classes do not need interfaces.
  * Try to write real objects, not procedural code.
  * Reject nulls. Don't return them, don't use them (some external libraries like them).
  * Prefer "immutable" classes. Don't use setters that allow an object to be set up in a bad state, only use them for optional fields or where the usage requires mutability.
  * Use final on local variables and parameters.
  * Make all classes final, unless they've been designed for inheritance. The checkstyle rules will pick this up.

## Spec/test code ##

  * Don't add methods to the super class test case. Prefer static imports.
  * Use the SubjectCreator to create test subjects. This will handle dependencies. Eventually subjects will be autocreated.
  * Ensure code is policy checked (nulls, finals, etc.).
  * Mark all actors with their rolls, make use of the @Mock, @Dummy, @Stub annotations.

# Committing Code #

Before committing code, make sure that you have followed the guidelines below, keep the code clean, and run a full build of the core (see above).

If you add any new features, add them to the [Roadmap](Roadmap.md) for the current release. These are copied verbatim into the release notes, so ensure they are accurate and concise.

# Features #

**Any new features should have an example of their use placed into the example project.**

# Packages #

Instinct is organised into published APIs and internal packages. Add any user-facing code into the top-level packages, other code belongs in `internal`.

## Published packages ##

`com.googlecode.instinct.actor`
> Utilities for creating specification actors; dummies, stubs and mocks.

`com.googlecode.instinct.expect`
> Expectation API -Includes support for setting state & behaviour expectations.

`com.googlecode.instinct.integrate`
> Integration with other tools such as JUnit and Ant.

`com.googlecode.instinct.marker`
> Annotations and naming conventions for making code available to Instinct.


## Internal packages ##

`com.googlecode.instinct.internal`
> All classes in this package are internal Instinct APIs. Relying on them is not
> recommended.

`com.googlecode.instinct.sandbox`
> New ideas, not ready for production usage. You are strongly advised not to
> use this code.


## Plug-in Points ##

`com.googlecode.instinct.report`
> Context result reporting.

`com.googlecode.instinct.runner`
> Context runners for running Instinct contexts outside of the provided Ant,
> JUnit or IDE runners.