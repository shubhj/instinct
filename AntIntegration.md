

<[User's Guide](UsersGuide.md)>

Instinct comes with two Ant tasks. The first of these `<instinct>` executes your specifications. The second `<instinct-report>` generates an HTML report on the specification results.

# Integrating with Ant #

  1. Download Instinct.
  1. Ensure you have the Ant 1.7.0 Jar in your classpath.
  1. Add the Instinct jar and required libraries to your classpath
  1. Within your build file, define the Instinct tasks so that Ant can see them.
  1. Create a target for Instinct that runs after your main code and specification code is compiled. Within this target, run the `instinct` task, pointing it at the directory containing the (compiled) specification classes.
  1. Create a target for Instinct Report that runs after the Instinct task. Within this target, run the `instinct-report` task, pointing it at a fileset of XML report files (an output of the Instinct task).

The following sections take you through these steps in detail. The [example project](http://code.google.com/p/instinct/downloads/list) contains a working example of this build.

## Add Required Libraries to the Classpath ##

Before the Ant tasks can be run, you will need to define a classpath so that Instinct can find your classes and the classes it needs to run. The classes that will go into this classpath will include any classes your specifications will access (such as the production code under scrutiny, libraries used, etc.) as well as libraries required by Instinct. Details for what libraries are required when can be found in the [project readme](http://instinct.googlecode.com/svn/trunk/core/README).

The following example is the minimal set required for running from Ant.

```
<path id="spec.class.path">
    <path refid="main.class.path"/>
    <fileset dir="${basedir}">
        <include name="${lib.dir}/cglib-nodep-*.jar"/>
        <include name="${lib.dir}/jmock-*.jar"/>
        <include name="${lib.dir}/objenesis-*.jar"/>
        <include name="${lib.dir}/ant-1.*.jar"/>
        <include name="${lib.dir}/hamcrest-all-*.jar"/>
        <include name="${lib.dir}/functionaljava-*.jar"/>
        <include name="${lib.dir}/junit-*.jar"/>
        <include name="${lib.dir}/commons-collections-*.jar"/><!-- as of instinct v0.20.0 -->
        <include name="${lib.dir}/commons-lang-*.jar"/><!-- as of instinct v0.20.0 -->
        <include name="${lib.dir}/velocity-*.jar"/><!-- as of instinct v0.20.0 -->
    </fileset>
    <pathelement location="${spec.classes.dir}"/>
</path>
```

## Using the Instinct Ant Tasks ##

In order to use the Instinct tasks, you first need to make them available to Ant, using the [taskdef](http://ant.apache.org/manual/CoreTasks/taskdef.html) element.

```
<taskdef name="instinct" classname="com.googlecode.instinct.integrate.ant.InstinctAntTask" classpathref="spec.class.path"/>
```

You could also use the resource form of the taskdef element:

```
<taskdef resource="instincttask.properties" classpathref="spec.class.path"/>
```

### The Instinct Task ###
After defining the task, use the `instinct` task to run your specifications:

```
<instinct failureproperty="specs-failed">
    <classpath refid="spec.class.path"/>
    <specifications dir="${spec.classes.dir}" groups="osdc"/>
    <formatter type="brief"/>
    <formatter type="xml" toDir="${spec.output.dir}"/>
</instinct>
```

The elements used in the `instinct` task are as follows:

  * Element `instinct` - The Instinct Ant task.
    * Attribute `failureproperty` - The name of the property to set if any specifications fail. You need to combine this with a `<fail/>` element if you want the build to fail when a test fails (see the complete example below for details).
  * Element `specifications` - This element tells Instinct how to find the specifications to run. Specifications are currently found based on annotations. You can have as many `specifications` as you like, Instinct will search them all for specifications to run. This is useful if you keep your specifications in different source trees.
    * Attribute `dir` - The directory containing the (compiled) specification classes. This is the root directory of the package containing the specifications.
  * Element `formatter` - Tells Instinct how to format the results of the specifications it runs. Since v0.20.0 you can specify multiple formatters for each `instinct` task.
    * Attribute `type` - The type of output format to use. Supported output formats are "brief", "quiet", "verbose" and "xml".
      * The `brief` formatter displays the results of behaviour context on on a single line, with a summary of all the contained specifications.
      * The `quiet` formatter prints failures and a summary only.
      * The `verbose` formatter displays a summary of each context on one line (similar to the brief formatter), followed by details of each of the contained specifications. Failures include the full stack trace of the failure, making output quite verbose.
      * The `xml` formatter (available in v0.20.0 onwards) displays the result in an XML format compatible with XML output of the JUnit task.
    * Attribute `toDir` - If set to a writable directory, this optional attribute will direct the formatter's output to a file in that directory instead of to the console.

Example of brief output:

```
-run-specs:
 [instinct] AGlossyMagazine
 [instinct] - mustSaveTheTitleGivenInConstructor
 [instinct] ANonEmptyStack
 [instinct] - isNoLongerFullAfterPoppingAllElements
 [instinct] - mustNoLongerBeFullAfterPop
 [instinct] AnEmptyMagazineRack
 [instinct] - callsPushOnStackWhenAddAMagazineIsAddedToThePile
 [instinct] AnEmptyShoppingCart
 [instinct] - doesNotFailWhenAnItemIsRemovedFromIt
 [instinct] - shouldBeEmpty
 [instinct] - canHaveMultipleItemsAddedToIt
 [instinct] - canHaveAnItemAddedToIt
 [instinct] AnEmptyStack
 [instinct] - willReturnTheSameObjectWhenPushed
 [instinct] - mustNoLongerBeEmptyAfterPush
 [instinct] - mustBeEmpty
 [instinct] StateExpectationsExample
 [instinct] - providesMatchersForMakingAssertionsAboutEvents
 [instinct] - providesMatchersForMakingAssertionsAboutDoubles
 [instinct] - providesMatchersForMakingAssertionsAboutMaps
 [instinct] - providesMatchersForMakingAssertionsAboutArrays
 [instinct] - providesMatchersForMakingAssertionsAboutClasses
 [instinct] - providesMatchersForMakingAssertionsAboutCollectionsAndIterables
 [instinct] - providesMatchersForMakingAssertionsAboutComparables
 [instinct] - providesMatchersForMakingAssertionsAboutObjects
 [instinct] - providesMatchersForMakingAssertionsAboutStrings
```

Example of verbose output:

```
-run-specs:
 [instinct] AGlossyMagazine, Specifications run: 1, Successes: 1, Failures: 0, Total time elapsed: 0.016 seconds
 [instinct]     mustSaveTheTitleGivenInConstructor, Time elapsed: 0.016 seconds, Status: succeeded
 [instinct] ANonEmptyStack, Specifications run: 2, Successes: 2, Failures: 0, Total time elapsed: 0.0020 seconds
 [instinct]     isNoLongerFullAfterPoppingAllElements, Time elapsed: 0.0020 seconds, Status: succeeded
 [instinct]     mustNoLongerBeFullAfterPop, Time elapsed: 0.0 seconds, Status: succeeded
 [instinct] AnEmptyMagazineRack, Specifications run: 1, Successes: 1, Failures: 0, Total time elapsed: 0.262 seconds
 [instinct]     callsPushOnStackWhenAddAMagazineIsAddedToThePile, Time elapsed: 0.262 seconds, Status: succeeded
 [instinct] AnEmptyShoppingCart, Specifications run: 4, Successes: 4, Failures: 0, Total time elapsed: 0.039 seconds
 [instinct]     doesNotFailWhenAnItemIsRemovedFromIt, Time elapsed: 0.017 seconds, Status: succeeded
 [instinct]     shouldBeEmpty, Time elapsed: 0.0080 seconds, Status: succeeded
 [instinct]     canHaveMultipleItemsAddedToIt, Time elapsed: 0.0080 seconds, Status: succeeded
 [instinct]     canHaveAnItemAddedToIt, Time elapsed: 0.0060 seconds, Status: succeeded
 [instinct] AnEmptyStack, Specifications run: 3, Successes: 3, Failures: 0, Total time elapsed: 0.0010 seconds
 [instinct]     willReturnTheSameObjectWhenPushed, Time elapsed: 0.0010 seconds, Status: succeeded
 [instinct]     mustNoLongerBeEmptyAfterPush, Time elapsed: 0.0 seconds, Status: succeeded
 [instinct]     mustBeEmpty, Time elapsed: 0.0 seconds, Status: succeeded
 [instinct] StateExpectationsExample, Specifications run: 9, Successes: 9, Failures: 0, Total time elapsed: 0.029 seconds
 [instinct]     providesMatchersForMakingAssertionsAboutEvents, Time elapsed: 0.0030 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutDoubles, Time elapsed: 0.0020 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutMaps, Time elapsed: 0.0040 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutArrays, Time elapsed: 0.0040 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutClasses, Time elapsed: 0.0020 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutCollectionsAndIterables, Time elapsed: 0.0040 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutComparables, Time elapsed: 0.0010 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutObjects, Time elapsed: 0.0050 seconds, Status: succeeded
 [instinct]     providesMatchersForMakingAssertionsAboutStrings, Time elapsed: 0.0040 seconds, Status: succeeded
```

### The Instinct Report Task ###
After executing the instinct task with XML file output, use the `instinct-report` task on those XML files to generate an HTML report of your specification results:

```
<instinct-report file="${spec.reports.dir}/report.html">
    <fileset dir="${spec.output.dir}">
        <include name="SPEC-*.xml"/>
    </fileset>
</instinct-report>
```

The elements used in the `instinct-report` task are as follows:

  * Element `instinct-report` - The Instinct Report Ant task.
    * Attribute `file` - The report file to be generated.
  * Element `fileset` - a [fileset](http://ant.apache.org/manual/CoreTypes/fileset.html) identifying the XML output files generated by the `instinct` task.

Example of a specification result:

![http://instinct.googlecode.com/svn/wiki/images/report.passed.png](http://instinct.googlecode.com/svn/wiki/images/report.passed.png)

Example of a specification result with a failed specification:

![http://instinct.googlecode.com/svn/wiki/images/report.failed.png](http://instinct.googlecode.com/svn/wiki/images/report.failed.png)


## Complete Example ##

Here is an example of a build file (build.xml) taken from the example project.

```
<?xml version="1.0" encoding="UTF-8"?>
<project name="instinct-example" default="all" basedir=".">
    <property name="project.longname" value="Instinct Behaviour Driven Development (BDD) Framework Examples"/>
    <property name="project.shortname" value="instinct-example"/>
    <property name="project.version.major" value="0"/>
    <property name="project.version.minor" value="2.0"/>
    <property name="version-status" value=""/>
    <property name="project.version.full" value="${project.version.major}.${project.version.minor}${version-status}"/>

    <property name="build.dir" value="build"/>
    <property name="lib.dir" value="lib"/>
    <property name="src.dir" value="src"/>
    <property name="main.dir" value="${src.dir}/main"/>
    <property name="spec.dir" value="${src.dir}/spec"/>
    <property name="java.main.src.dir" value="${main.dir}/java"/>
    <property name="java.spec.src.dir" value="${spec.dir}/java"/>
    <property name="scala.main.src.dir" value="${main.dir}/scala"/>
    <property name="scala.spec.src.dir" value="${spec.dir}/scala"/>
    <property name="spec.resources.dir" value="${spec.dir}/resources"/>
    <property name="main.classes.dir" value="${build.dir}/main-classes"/>
    <property name="spec.classes.dir" value="${build.dir}/spec-classes"/>
    <property name="spec.reports.dir" value="${basedir}/build/reports"/>
    <property name="release.dir" value="${build.dir}/release"/>

    <property name="project.zip" value="${project.shortname}-${project.version.full}.zip"/>
    <property name="instinct.jar" value="${lib.dir}/instinct-core-${project.version.full}.jar"/>

    <path id="java.main.class.path">
        <fileset dir="${basedir}">
            <include name="${instinct.jar}"/>
        </fileset>
        <pathelement location="${main.classes.dir}"/>
    </path>
    <path id="scala.main.class.path">
        <path refid="java.main.class.path"/>
        <fileset dir="${basedir}">
            <include name="${lib.dir}/scala-*.jar"/>
        </fileset>
    </path>
    <path id="java.spec.class.path">
        <path refid="java.main.class.path"/>
        <fileset dir="${basedir}">
            <include name="${lib.dir}/cglib-nodep-*.jar"/>
            <include name="${lib.dir}/jmock-*.jar"/>
            <include name="${lib.dir}/objenesis-*.jar"/>
            <include name="${lib.dir}/ant-1.*.jar"/>
            <include name="${lib.dir}/hamcrest-all-*.jar"/>
            <include name="${lib.dir}/junit-*.jar"/>
            <include name="${lib.dir}/functionaljava-*.jar"/>
            <include name="${lib.dir}/commons-collections-*.jar"/>
            <include name="${lib.dir}/commons-lang-*.jar"/>
            <include name="${lib.dir}/velocity-*.jar"/>
        </fileset>
        <pathelement location="${spec.classes.dir}"/>
        <pathelement location="${spec.resources.dir}"/>
    </path>
    <path id="scala.spec.class.path">
        <path refid="scala.main.class.path"/>
        <path refid="java.spec.class.path"/>
    </path>

    <taskdef resource="instincttask.properties" classpathref="scala.spec.class.path"/>
    <taskdef resource="scala/tools/ant/antlib.xml" classpathref="scala.main.class.path"/>

    <target name="all" depends="clean,release"/>
    <target name="clean" depends="-clean"/>
    <target name="run-specs" depends="clean,-run-specs,-run-junit3-integration,-run-junit4-integration,report"/>
    <target name="release" depends="clean,run-specs,-release-zip"/>

    <target name="-clean">
        <delete dir="${build.dir}"/>
    </target>

    <target name="-generate">
        <copy todir="${spec.classes.dir}">
            <fileset dir="${spec.resources.dir}" includes="**/*"/>
        </copy>
    </target>

    <target name="-release-zip">
        <mkdir dir="${release.dir}"/>
        <zip destfile="${release.dir}/${project.zip}" filesonly="true">
            <fileset dir="${basedir}" includes="**/*" excludes="**/*.svn,${build.dir}/**/*,i-build/**/*"/>
        </zip>
    </target>

    <target name="-compile-java" depends="-generate">
        <compile.java.macro classpath.ref="java.main.class.path" src.dir="${java.main.src.dir}" output.dir="${main.classes.dir}"
                src.path="${java.main.src.dir}"/>
        <compile.java.macro classpath.ref="java.spec.class.path" src.dir="${java.spec.src.dir}" output.dir="${spec.classes.dir}"
                src.path="${java.spec.src.dir}"/>
    </target>

    <target name="-compile-scala" depends="-generate">
        <compile.scala.macro classpath.ref="scala.main.class.path" output.dir="${main.classes.dir}" src.path="${scala.main.src.dir}"/>
        <compile.scala.macro classpath.ref="scala.spec.class.path" output.dir="${spec.classes.dir}" src.path="${scala.spec.src.dir}"/>
    </target>

    <target name="-run-specs" depends="-compile-java,-compile-scala">
        <mkdir dir="${spec.reports.dir}"/>
        <instinct failureproperty="specs-failed">
            <classpath refid="scala.spec.class.path"/>
            <specifications dir="${spec.classes.dir}" groups="osdc"/>
            <formatter type="brief"/>
            <formatter type="xml" toDir="${spec.reports.dir}"/>
        </instinct>
        <fail if="specs-failed" message="Specifications failed."/>
    </target>

    <target name="-run-junit3-integration" depends="-compile-java">
        <junit failureproperty="tests-failed" fork="true" forkmode="perBatch">
            <classpath refid="java.spec.class.path"/>
            <test name="com.googlecode.instinct.example.junit.StackJUnitSuite"/>
            <formatter type="brief" usefile="false"/>
        </junit>
        <fail if="tests-failed" message="JUnit 3 integration suite(s) failed."/>
    </target>

    <target name="-run-junit4-integration" depends="-compile-java">
        <junit failureproperty="tests-failed" fork="true" forkmode="perBatch">
            <classpath refid="java.spec.class.path"/>
            <test name="com.googlecode.instinct.example.junit.StackJUnit4Suite"/>
            <test name="com.googlecode.instinct.example.csvreader.junit.CsvReaderJUnitTests"/>
            <formatter type="brief" usefile="false"/>
        </junit>
        <fail if="tests-failed" message="JUnit 4 integration suite(s) failed."/>
    </target>

    <target name="report">
        <instinct-report file="${spec.reports.dir}/report.html">
            <fileset dir="${spec.reports.dir}">
                <include name="SPEC-*.xml"/>
            </fileset>
        </instinct-report>
    </target>

    <macrodef name="compile.java.macro">
        <attribute name="src.dir"/>
        <attribute name="output.dir"/>
        <attribute name="classpath.ref"/>
        <attribute name="src.path"/>
        <sequential>
            <mkdir dir="@{output.dir}"/>
            <javac classpathref="@{classpath.ref}" source="1.5" srcdir="@{src.dir}" destdir="@{output.dir}" debug="true"
                    debuglevel="source,lines,vars" deprecation="false" optimize="true">
                <src>
                    <path path="@{src.path}"/>
                </src>
                <patternset includes="**/*.java"/>
                <compilerarg value="-Xlint"/>
            </javac>
        </sequential>
    </macrodef>

</project>
```

<[User's Guide](UsersGuide.md)>