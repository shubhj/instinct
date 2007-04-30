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

package com.googlecode.instinct.integrate.idea;

import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.CommandLineRunner;
import static com.googlecode.instinct.runner.CommandLineRunner.METHOD_SEPARATOR;
import com.intellij.execution.CantRunException;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.ConfigurationPerRunnerSettings;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunnerSettings;

public final class ContextRunCommandLineState extends JavaCommandLineState {
    private static final String COMMAND_LINE_RUNNER_CLASS_NAME = CommandLineRunner.class.getName();
    private InstinctRunConfiguration runConfiguration;

    @SuppressWarnings({"RawUseOfParameterizedType"})
    public ContextRunCommandLineState(final InstinctRunConfiguration runConfiguration, final RunnerSettings runner,
            final ConfigurationPerRunnerSettings configuration) {
        super(runner, configuration);
        this.runConfiguration = runConfiguration;
    }

    @Override
    public JavaParameters createJavaParameters() throws ExecutionException {
        final String contextClassName = runConfiguration.getContextClassName();
        final String specificationMethod = runConfiguration.getSpecificationMethodName();
        final String specificationToRun = createSpecificationsToRunArgument(contextClassName, specificationMethod);
        return createParameters(specificationToRun);
    }

    @Suggest("Move this into a shared class, parsing & creating belong in the same class.")
    private String createSpecificationsToRunArgument(final String contextClassName, final String specificationMethodName) {
        if (specificationMethodName.length() == 0) {
            return contextClassName;
        } else {
            return contextClassName + METHOD_SEPARATOR + specificationMethodName;
        }
    }

    private JavaParameters createParameters(final String specificationsToRun) throws CantRunException {
        final JavaParameters parameters = new JavaParameters();
        parameters.setMainClass(COMMAND_LINE_RUNNER_CLASS_NAME);
        parameters.getProgramParametersList().addParametersString(specificationsToRun);
        parameters.configureByModule(runConfiguration.getModule(), JavaParameters.JDK_AND_CLASSES_AND_TESTS);
        parameters.setWorkingDirectory(runConfiguration.getWorkingDirectoryPath());
        return parameters;
    }
}
