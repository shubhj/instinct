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
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.param.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.runner.CommandLineRunner;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.LogStreamHandler;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

@SuppressWarnings({"MethodParameterOfConcreteClass", "InstanceVariableOfConcreteClass"})
public final class InstinctAntTask extends Task implements StatusLogger {
    private final List<Specifications> specificationLocators = new ArrayList<Specifications>();
    private String failureProperty;
    private Formatter formatter;
    private CommandlineJava javaCommandLine;

    public void setFailureProperty(final String failureProperty) {
        checkNotWhitespace(failureProperty);
        this.failureProperty = failureProperty;
    }

    public void addSpecifications(final Specifications specificationLocator) {
        checkNotNull(specificationLocator);
        specificationLocators.add(specificationLocator);
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

    @Override
    public void execute() throws BuildException {
        checkExecutePreconditions();
        doExecute();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    private CommandlineJava getJavaCommandLine() {
        if (javaCommandLine == null) {
            javaCommandLine = new CommandlineJava();
        }
        return javaCommandLine;
    }

    @SuppressWarnings({"CatchGenericClass"})
    // SUPPRESS IllegalCatch {
    private void doExecute() {
        try {
            runContexts();
        } catch (Throwable t) {
            throw new BuildException(t);
        }
    }
    // } SUPPRESS IllegalCatch

    @Fix("Register as a runner, so that we recieve results as it happens.")
    // TODO The runners also need to be passed a group, so they don't run the wrong thing.
    private void runContexts() {
        final CommandlineJava commandLine = createCommandLine();
        executeAsForked(commandLine);
    }

    private CommandlineJava createCommandLine() {
//        addInstinctClassesToClasspath();
        final CommandlineJava commandLine = getJavaCommandLine();
        commandLine.setClassname(CommandLineRunner.class.getName());
        final String contexts = getContextNamesToRun();
        commandLine.createArgument().setValue(contexts);
        return commandLine;
    }

//    private void addInstinctClassesToClasspath() {
////        addClassToClassPath(CommandLineRunner.class);
////        addClassToClassPath(EdgeException.class);
//    }

//    private <T> void addClassToClassPath(final Class<T> cls) {
//        final Path path = new Path(getProject());
//        path.setLocation(findClass(cls));
//        createClasspath().add(path);
//    }

//    private <T> File findClass(final Class<T> cls) {
//        final String pathName = cls.getName().replace(".", "/") + ".class";
//        final File source = LoaderUtils.getResourceSource(getClass().getClassLoader(), pathName);
//        if (source == null) {
//            throw new BuildException("Unable");
//        }
//        return source;
//    }

    private String getContextNamesToRun() {
        final List<JavaClassName> contextClasses = findContextsFromAllAggregators();
        final StringBuilder builder = new StringBuilder();
        for (final Iterator<JavaClassName> iterator = contextClasses.iterator(); iterator.hasNext();) {
            final JavaClassName contextClass = iterator.next();
            builder.append(contextClass.getFullyQualifiedName());
            if (iterator.hasNext()) {
                builder.append(RunnableItemBuilder.ITEM_SEPARATOR);
            }
        }
        return builder.toString();
    }

    private List<JavaClassName> findContextsFromAllAggregators() {
        final List<JavaClassName> contextClasses = new ArrayList<JavaClassName>();
        for (final Specifications specificationLocator : specificationLocators) {
            contextClasses.addAll(asList(specificationLocator.getContextClasses()));
        }
        return contextClasses;
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
