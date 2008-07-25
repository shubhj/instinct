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

import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import com.googlecode.instinct.internal.util.Fix;
import static com.googlecode.instinct.internal.util.Fj.toFjList;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.runner.CommandLineRunner;
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
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

@SuppressWarnings({"MethodParameterOfConcreteClass", "InstanceVariableOfConcreteClass"})
public final class InstinctAntTask extends Task implements StatusLogger {
    private List<Specifications> specificationLocators = nil();
    private String failureProperty;
    private Formatter formatter;
    private CommandlineJava javaCommandLine;

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
        checkFormatterNotAlreadyAssigned();
        this.formatter = formatter;
    }

    public Path createClasspath() {
        return getJavaCommandLine().createClasspath(getProject()).createPath();
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

    private CommandlineJava getJavaCommandLine() {
        if (javaCommandLine == null) {
            javaCommandLine = new CommandlineJava();
        }
        return javaCommandLine;
    }

    @Fix("Register as a runner, so that we recieve results as it happens.")
    // TODO The runners also need to be passed a group, so they don't run the wrong thing.
    private void runContexts() {
        final CommandlineJava commandLine = createCommandLine();
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

    private CommandlineJava createCommandLine() {
        final CommandlineJava commandLine = getJavaCommandLine();
        commandLine.setClassname(CommandLineRunner.class.getName());
        final String contexts = getContextNamesToRun();
        commandLine.createArgument().setValue(contexts);
        return commandLine;
    }

    private String getContextNamesToRun() {
        final List<List<Character>> contexts = findContextsFromAllAggregators().map(new F<JavaClassName, List<Character>>() {
            public List<Character> f(final JavaClassName contextClass) {
                return fromString(contextClass.getFullyQualifiedName());
            }
        });
        return asString(join(contexts.intersperse(fromString(RunnableItemBuilder.ITEM_SEPARATOR))));
    }

    private List<JavaClassName> findContextsFromAllAggregators() {
        return join(specificationLocators.map(new F<Specifications, List<JavaClassName>>() {
            public List<JavaClassName> f(final Specifications locator) {
                return toFjList(locator.getContextClasses());
            }
        }));
    }

    private void checkExecutePreconditions() {
        if (failureProperty == null) {
            throw new IllegalStateException("Attribute failureProperty must be specified");
        }
    }

    private void checkFormatterNotAlreadyAssigned() {
        if (formatter != null) {
            throw new IllegalStateException("Only one formatter element is allowed");
        }
    }
}
