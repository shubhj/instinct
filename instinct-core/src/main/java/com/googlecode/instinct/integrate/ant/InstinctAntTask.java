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
import au.net.netstorm.boost.edge.java.lang.DefaultEdgeClass;
import au.net.netstorm.boost.edge.java.lang.EdgeClass;
import com.googlecode.instinct.internal.runner.BehaviourContextResult;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.runner.BehaviourContextRunnerImpl;
import com.googlecode.instinct.internal.util.JavaClassName;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import com.googlecode.instinct.internal.util.Suggest;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

@Suggest({"Will probably need formatters that are specified in the build config", "brief (what is there now)", "plain - each spec on a new line",
        "verbose"})
public final class InstinctAntTask extends Task {
    private final List<SpecificationAggregator> aggregators = new ArrayList<SpecificationAggregator>();
    private final EdgeClass edgeClass = new DefaultEdgeClass();
    private final BehaviourContextRunner behaviourContextRunner = new BehaviourContextRunnerImpl();
    private String failureProperty;

    public void setFailureProperty(final String failureProperty) {
        checkNotWhitespace(failureProperty);
        this.failureProperty = failureProperty;
    }

    @SuppressWarnings({"MethodParameterOfConcreteClass"})
    public void addAnnotatedSpecificationAggregator(final AnnotatedSpecificationAggregatorImpl aggregator) {
        checkNotNull(aggregator);
        aggregators.add(aggregator);
    }

    @Override
    public void execute() throws BuildException {
        checkPreconditions();
        doExecute();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @SuppressWarnings({"CatchGenericClass"})
    // DEBT IllegalCatch {
    private void doExecute() {
        try {
            runContexts();
        } catch (Throwable e) {
            throw new BuildException(e);
        }
    }
    // } DEBT IllegalCatch

    private void runContexts() {
        final List<JavaClassName> contextClasses = findBehaviourContextsFromAllAggregators();
        runAllContexts(contextClasses);
    }

    @Suggest("Cleanup - do something with the runner")
    private void runAllContexts(final List<JavaClassName> contextClasses) {
        final BehaviourContextRunner runner = new StatusLoggingBehaviourContextRunner(behaviourContextRunner, new AntStatusLogger(getProject()));
        log("foo");
        for (final JavaClassName contextClass : contextClasses) {
            final Class<?> cls = edgeClass.forName(contextClass.getFullyQualifiedName());
            final BehaviourContextResult result = runner.run(cls);
            if (!result.completedSuccessfully()) {
                getProject().setProperty(failureProperty, "true");
            }
        }
    }

    private List<JavaClassName> findBehaviourContextsFromAllAggregators() {
        final List<JavaClassName> contextClasses = new ArrayList<JavaClassName>();
        for (final SpecificationAggregator aggregator : aggregators) {
            contextClasses.addAll(asList(aggregator.getContextNames()));
        }
        return contextClasses;
    }

    private void checkPreconditions() {
        if (failureProperty == null) {
            throw new IllegalStateException("Attribute failureProperty must be specified");
        }
    }
}