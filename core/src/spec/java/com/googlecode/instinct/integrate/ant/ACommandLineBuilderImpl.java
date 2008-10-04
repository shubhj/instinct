/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.integrate.ant;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.report.ResultFormat;
import fj.data.List;
import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ACommandLineBuilderImpl {

    @Subject(implementation = CommandLineBuilderImpl.class)
    private CommandLineBuilder builder;

    @BeforeSpecification
    public void setUp() {
        builder = new CommandLineBuilderImpl();
    }

    @Specification
    public void willCreatePathEvenWhenProjectIsNull() {
        final Path path = builder.createClasspath(null);
        expect.that(path).isNotNull();
        expect.that(path.getProject()).isNull();
    }

    @Specification
    public void willCreatePathWithGivenProject() {
        final Project project = new Project();
        final Path path = builder.createClasspath(project);
        expect.that(path).isNotNull();
        expect.that(path.getProject()).isEqualTo(project);
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 1 should not be null")
    public void willRefuseToBuildCommandLineJavaWithNullFormatters() {
        builder.build(null, List.<Specifications>nil());
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Parameter 2 should not be null")
    public void willRefuseToBuildCommandLineJavaWithNullSpecifications() {
        builder.build(List.<Formatter>nil(), null);
    }

    @Specification
    public void willBuildACommandWithNoParametersIfFormattersAndSpecificationsAreNotProvided() {
        final CommandlineJava commandLine = builder.build(List.<Formatter>nil(), List.<Specifications>nil());
        expect.that(commandLine.getCommandline().length).isEqualTo(3);
        expect.that(commandLine.getClassname()).isEqualTo("com.googlecode.instinct.runner.CommandLineRunner");
        expect.that(commandLine.getCommandline()[1]).isEqualTo(commandLine.getClassname());
        expect.that(commandLine.getCommandline()[2]).isEqualTo("");
    }

    @Specification
    public void willBuildACommandWithSingleFormatterProvided() {
        final CommandlineJava commandLine = builder.build(List.<Formatter>single(new Formatter(ResultFormat.QUIET)), List.<Specifications>nil());
        expect.that(commandLine.getCommandline().length).isEqualTo(5);
        expect.that(commandLine.getClassname()).isEqualTo("com.googlecode.instinct.runner.CommandLineRunner");
        expect.that(commandLine.getCommandline()[1]).isEqualTo(commandLine.getClassname());
        expect.that(commandLine.getCommandline()[2]).isEqualTo("--formatters");
        expect.that(commandLine.getCommandline()[3]).isEqualTo("QUIET");
        expect.that(commandLine.getCommandline()[4]).isEqualTo("");
    }

    @Specification
    public void willBuildACommandWithFormattersToDirParameterProvided() {
        final File dir = new File(".");
        final CommandlineJava commandLine = builder.build(List.<Formatter>single(new Formatter(ResultFormat.QUIET, dir)), List.<Specifications>nil());
        expect.that(commandLine.getCommandline().length).isEqualTo(5);
        expect.that(commandLine.getClassname()).isEqualTo("com.googlecode.instinct.runner.CommandLineRunner");
        expect.that(commandLine.getCommandline()[1]).isEqualTo(commandLine.getClassname());
        expect.that(commandLine.getCommandline()[2]).isEqualTo("--formatters");
        expect.that(commandLine.getCommandline()[3]).isEqualTo("QUIET{toDir=" + dir.getAbsolutePath() + "}");
        expect.that(commandLine.getCommandline()[4]).isEqualTo("");
    }

    @Specification
    public void willBuildAComplexCommandWithSingleComplexSpecificationsProvided() {
        final CommandlineJava commandLine = builder.build(List.<Formatter>nil(), List.<Specifications>single(SpecificationsFixture.any(this)));
        expect.that(commandLine.getCommandline().length).isEqualTo(3);
        expect.that(commandLine.getClassname()).isEqualTo("com.googlecode.instinct.runner.CommandLineRunner");
        expect.that(commandLine.getCommandline()[1]).isEqualTo(commandLine.getClassname());
        expect.that(commandLine.getCommandline()[2]).containsString(getClass().getName());
    }

    @Specification
    public void willBuildACommandWithMultipleFormatters() {
        final CommandlineJava commandLine = builder.build(
                List.<Formatter>list(new Formatter(ResultFormat.QUIET), new Formatter(ResultFormat.BRIEF), new Formatter(ResultFormat.XML)),
                List.<Specifications>nil());
        expect.that(commandLine.getCommandline().length).isEqualTo(5);
        expect.that(commandLine.getClassname()).isEqualTo("com.googlecode.instinct.runner.CommandLineRunner");
        expect.that(commandLine.getCommandline()[1]).isEqualTo(commandLine.getClassname());
        expect.that(commandLine.getCommandline()[2]).isEqualTo("--formatters");
        expect.that(commandLine.getCommandline()[3]).isEqualTo("QUIET,BRIEF,XML");
        expect.that(commandLine.getCommandline()[4]).isEqualTo("");
    }
}
