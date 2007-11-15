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

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdge;
import com.googlecode.instinct.internal.edge.java.lang.reflect.ClassEdgeImpl;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.StandardContextRunner;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.internal.util.Suggest;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;

@SuppressWarnings({"MethodParameterOfConcreteClass", "InstanceVariableOfConcreteClass"})
public final class InstinctAntTask extends Task implements StatusLogger {
    private final List<Specifications> specificationLocators = new ArrayList<Specifications>();
    private final ClassEdge classEdge = new ClassEdgeImpl();
    private String failureProperty;
    private Formatter formatter;

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

    @Suggest("Fix this...")
    public Path createClasspath() {
        final CommandlineJava commandLine = new CommandlineJava();
        return commandLine.createClasspath(getProject()).createPath();
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
    private void runContexts() {
        final List<JavaClassName> contextClasses = findContextsFromAllAggregators();
        final ContextRunner runner = new AntContextRunner(new StandardContextRunner(), formatter.createMessageBuilder(), this);
        runContexts(runner, contextClasses);
    }

    private List<JavaClassName> findContextsFromAllAggregators() {
        final List<JavaClassName> contextClasses = new ArrayList<JavaClassName>();
        for (final Specifications specificationLocator : specificationLocators) {
            contextClasses.addAll(asList(specificationLocator.getContextClasses()));
        }
        return contextClasses;
    }

    private void runContexts(final ContextRunner contextRunner, final List<JavaClassName> contextClasses) {
        for (final JavaClassName contextClass : contextClasses) {
            runContext(contextRunner, contextClass);
        }
    }

    private void runContext(final ContextRunner runner, final JavaClassName contextClass) {
        final Class<?> cls = classEdge.forName(contextClass.getFullyQualifiedName());
        final ContextResult result = runner.run(new ContextClassImpl(cls));
        if (!result.completedSuccessfully()) {
            getProject().setProperty(failureProperty, "true");
        }
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
