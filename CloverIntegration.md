<[User's Guide](UsersGuide.md)>

# Integrating with Clover #

[Atlassian's Clover](http://www.atlassian.com/software/clover/) is a code coverage tool that is used in many Java projects to monitor the level of test coverage production code has. Clover natively supports JUnit and TestNG, however it may also be used with Instinct, provided you configure it correctly.

The following example shows how to integrate Clover into an Ant build. Note that this example comes from a real project so may be slightly more complicated than a basic setup (you may choose to use the default database location for example).

```
<property name="lib.dir" value="lib"/>
<property name="src.dir" value="src"/>
<property name="main.src.dir" value="${src.dir}/main/java"/>
<property name="spec.src.dir" value="${src.dir}/spec/java"/>

<property name="coverage.results.dir" value="${build.dir}/coverage"/>
<property name="coverage.db" value="${coverage.results.dir}/coverage.db"/>
<property name="coverage.flush.interval" value="1000"/>
<property name="coverage.target" value="85%"/>

<taskdef resource="cloverlib.xml" classpath="${lib.dir}/clover/clover-ant-2.0.3.jar"/>

<fileset id="main.covered.fileset" dir="${main.src.dir}">
    <include name="**/*.java"/>
</fileset>
<fileset id="spec.fileset" dir="${spec.src.dir}">
    <include name="**/*.java"/>
</fileset>

<target name="-enable-clover">
    <clover-setup initstring="${coverage.db}" flushpolicy="threaded" flushinterval="${coverage.flush.interval}">
        <fileset refid="main.covered.fileset"/>
        <testsources dir="${spec.src.dir}">
            <testclass>
                <testmethod annotation="Specification"/>
                <testmethod name="^should.*"/>
                <testmethod name="^must.*"/>
            </testclass>
        </testsources>
    </clover-setup>
</target>

<target name="-coverage-report">
    <clover-report projectname="${project.name}" initstring="${coverage.db}">
        <current outfile="${coverage.results.dir}" title="${project.name} Code Coverage Report" includeFailedTestCoverage="true">
            <format type="html" showEmpty="true"/>
            <testsources refid="spec.fileset"/>
        </current>
    </clover-report>
</target>

<target name="-clovercheck">
    <clover-check initstring="${coverage.db}" target="${coverage.target}" failureProperty="coverage.failure"/>
    <fail if="coverage.failure" message="Specification coverage did not meet the target of ${coverage.target}."/>
</target>
```

Clover's
[test detection mechanism](http://confluence.atlassian.com/display/CLOVER/Unit+Test+Results+and+Per-Test+Coverage) also shows an example of Clover with Instinct.

Note that expected specification failures marked with `@Specification(expected = Exception.class)` do not work with the current version of Clover. This means that Clover will regard expected exceptions as failures, and not contribute them to coverage. To avoid this, you should include failed specs in your coverage by setting `<current includeFailedTestCoverage="true" ...>`.

This problem has been [reported to Atlassian](http://jira.atlassian.com/browse/CLOV-94).

Here is an example of a Clover report from an Instinct project.

![http://instinct.googlecode.com/svn/wiki/images/InstinctCloverFailingSpecification.png](http://instinct.googlecode.com/svn/wiki/images/InstinctCloverFailingSpecification.png)

<[User's Guide](UsersGuide.md)>