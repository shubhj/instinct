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
import java.util.List;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotWhitespace;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public final class InstinctAntTask extends Task {
    private final List<SpecificationAggregator> aggregators = new ArrayList<SpecificationAggregator>();
    private InstinctAntTaskDelegate taskDelegate = new InstinctAntTaskDelegateImpl();

    public void setFailureProperty(final String failureProperty) {
        checkNotWhitespace(failureProperty);
        taskDelegate.setFailureProperty(failureProperty);
    }

    @SuppressWarnings({"MethodParameterOfConcreteClass"})
    public void addAnnotatedSpecificationAggregator(final AnnotatedSpecificationAggregatorImpl aggregator) {
        checkNotNull(aggregator);
        aggregators.add(aggregator);
    }

    @Override
    public void execute() throws BuildException {
        //getDescription()
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
