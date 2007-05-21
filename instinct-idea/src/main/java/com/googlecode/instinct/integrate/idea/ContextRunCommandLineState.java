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

import static com.googlecode.instinct.internal.runner.RunnableItemBuilder.METHOD_SEPARATOR;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.runner.CommandLineRunner;
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
    @Suggest("Consider only pasing the fields we need, rather than runConfiguration.")
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

    @Suggest({"Append the classpath of the plugin (last?) so that we don't need to add instinct to the classpath of the project,", "or",
            "can use differerent versions of instinct in the plugin vs the project using we're running"})
    private JavaParameters createParameters(final String specificationsToRun) throws CantRunException {
        final JavaParameters parameters = new JavaParameters();
        parameters.setMainClass(COMMAND_LINE_RUNNER_CLASS_NAME);
        parameters.getProgramParametersList().addParametersString(specificationsToRun);
        parameters.configureByModule(runConfiguration.getModule(), JavaParameters.JDK_AND_CLASSES_AND_TESTS);
        parameters.setWorkingDirectory(runConfiguration.getWorkingDirectoryPath());
//        final PathsList classPath = parameters.getClassPath();
//        final IdeaPluginDescriptor ideaPluginDescriptor = PluginManager.getPlugin(PluginId.getId(""));
//        System.out.println(classPath.getPathList());
        return parameters;
    }

    @Suggest("Move this into a shared class, parsing & creating belong in the same class.")
    private String createSpecificationsToRunArgument(final String contextClassName, final String specificationMethodName) {
        return specificationMethodName.length() == 0 ? contextClassName : contextClassName + METHOD_SEPARATOR + specificationMethodName;
    }

/*
ClassPath (excl. JDK clases):
[/Users/tom/tmp/instinct-example/i-build/test
 /Users/tom/tmp/instinct-example/i-build/main
 /Users/tom/tmp/instinct-example/lib/junit-4.3.1.jar
 /Users/tom/tmp/instinct-example/lib/cglib-nodep-2.2_beta1.jar
 /Users/tom/tmp/instinct-example/lib/jmock-2.1.0-RC1.jar
 /Users/tom/tmp/instinct-example/lib/objenesis-1.0.jar
 /Users/tom/tmp/instinct-example/lib/jmock-cglib-1.2.0.jar
 /Users/tom/tmp/instinct-example/lib/instinct-0.1.4.jar
 /Users/tom/tmp/instinct-example/lib/jmock-objenesis-2.1.0-RC1.jar
 /Users/tom/tmp/instinct-example/lib/hamcrest-all-1.0.jar
 /Users/tom/tmp/instinct-example/lib/jmock-core-1.2.0.jar
 /Users/tom/tmp/instinct-example/lib/boost-982.jar]
*/
}
