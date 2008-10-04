/*
 * Copyright 2006-2007 Tom Adams
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

import com.googlecode.instinct.internal.runner.Formatter;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import fj.data.List;
import static fj.data.List.nil;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import static org.apache.tools.ant.taskdefs.Execute.isFailure;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

@SuppressWarnings({"MethodParameterOfConcreteClass", "InstanceVariableOfConcreteClass", "UnusedDeclaration"})
public final class InstinctAntTask extends Task implements StatusLogger {
    private List<Specifications> specificationLocators = nil();
    private String failureProperty;
    private List<Formatter> formatters = List.nil();
    private CommandLineBuilder commandLineBuilder = new CommandLineBuilderImpl();

    public void setFailureProperty(final String failureProperty) {
        checkNotWhitespace(failureProperty);
        this.failureProperty = failureProperty;
    }

    public void addSpecifications(final Specifications specificationLocator) {
        checkNotNull(specificationLocator);
        specificationLocators = specificationLocators.cons(specificationLocator);
    }

    public void addFormatter(final Formatter formatter) {
        checkNotNull(formatter);
        formatters = formatters.cons(formatter);
    }

    public Path createClasspath() {
        return commandLineBuilder.createClasspath(getProject()).createPath();
    }

    public void setClasspath(final Path classPath) {
        checkNotNull(classPath);
        createClasspath().append(classPath);
    }

    public void setClasspathRef(final Reference classPathRefId) {
        checkNotNull(classPathRefId);
        createClasspath().setRefid(classPathRefId);
    }

    // SUPPRESS IllegalCatch {
    @Override
    public void execute() throws BuildException {
        checkExecutePreconditions();
        try {
            runContexts();
        } catch (Throwable t) {
            throw new BuildException(t);
        }
    }

    // } SUPPRESS IllegalCatch

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Fix("Register as a runner, so that we recieve results as it happens.")
    // TODO The runners also need to be passed a group, so they don't run the wrong thing.
    private void runContexts() {
        final CommandlineJava commandLine = commandLineBuilder.build(formatters, specificationLocators);
        final int exitCode = executeAsForked(commandLine);
        if (isFailure(exitCode)) {
            // getProject().setNewProperty(failureProperty, "true");
            throw new BuildException("Forked VM failed with exit code: " + exitCode);
        }
    }

    private int executeAsForked(final CommandlineJava commandline) {
        final Execute execute = new Execute(new LogStreamHandler(this, Project.MSG_INFO, Project.MSG_WARN));
        execute.setCommandline(commandline.getCommandline());
        execute.setAntRun(getProject());
        log(commandline.describeCommand(), Project.MSG_VERBOSE);
        try {
            return execute.execute();
        } catch (IOException e) {
            throw new BuildException("Unable to run specifications", e, getLocation());
        }
    }

    private void checkExecutePreconditions() {
        if (failureProperty == null) {
            throw new IllegalStateException("Attribute failureProperty must be specified");
        }
    }
}
