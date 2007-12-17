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

package com.googlecode.instinct.runner;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.locate.ContextWithSpecsWithDifferentAccessModifiers;
import com.googlecode.instinct.internal.locate.cls.PackageRootFinder;
import com.googlecode.instinct.internal.locate.cls.PackageRootFinderImpl;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.RunnableItemBuilder;
import com.googlecode.instinct.internal.util.io.InputStreamConvertorImpl;
import com.googlecode.instinct.test.InstinctTestCase;
import com_cenqua_clover.Clover;
import java.io.File;
import static java.io.File.pathSeparatorChar;
import static java.io.File.separatorChar;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.sf.cglib.proxy.CallbackFilter;
import org.hamcrest.Matchers;
import org.jmock.api.ExpectationError;
import org.objenesis.Objenesis;

@SuppressWarnings({"HardcodedFileSeparator", "IOResourceOpenedButNotSafelyClosed"})
public final class CommandLineRunnerSlowTest extends InstinctTestCase {
    private static final Class<?> CONTEXT_CLASS_1 = ASimpleContext.class;
    private static final Class<?> CONTEXT_CLASS_2 = ContextWithSpecsWithDifferentAccessModifiers.class;
    private final PackageRootFinder packageRootFinder = new PackageRootFinderImpl();

    public void testDoesNotWOrkWithClover2OrWindows() {
        // This fails with Clover 2 and on Windows. Consider using the Ant libraries to do this instead of Java ones. 
    }

    public void nsoTestRunsContextsFromTheCommandLine() {
        checkRunContexts(CONTEXT_CLASS_1);
        checkRunContexts(CONTEXT_CLASS_2);
        checkRunContexts(CONTEXT_CLASS_1, CONTEXT_CLASS_2);
    }

    public void nsoTestRunsASingleSpecificationFromTheCommandLine() {
        checkRunSpecification(CONTEXT_CLASS_1, "toCheckVerification");
        checkRunSpecification(CONTEXT_CLASS_2, "notMe");
    }

    public void nsoTestRunsContextAndReportsErrors() {
        final Process process = runContexts(ContextWithFailingSpecs.class);
        expectThatRunnerReportsNoErrors(read(process.getErrorStream()));
        final String runnerOutput = new String(read(process.getInputStream()));
        expect.that(runnerOutput).containsString("FAILED");
    }

    private void checkRunContexts(final Class<?>... contextClasses) {
        final Process process = runContexts(contextClasses);
        expectThatRunnerReportsNoErrors(read(process.getErrorStream()));
        expectThatRunnerSendsSpeciciationResultsToOutput(read(process.getInputStream()), "", contextClasses);
    }

    private void checkRunSpecification(final Class<?> contextClass, final String specificationMethod) {
        final Process process = runSpecification(contextClass, specificationMethod);
        expectThatRunnerReportsNoErrors(read(process.getErrorStream()));
        expectThatRunnerSendsSpeciciationResultsToOutput(read(process.getInputStream()), specificationMethod);
    }

    private Process runContexts(final Class<?>... contextClass) {
        final ProcessBuilder processBuilder = createProcessBuilder(contextClass);
        return startProcess(processBuilder);
    }

    private <T> Process runSpecification(final Class<T> contextClass, final String specificationMethod) {
        final ProcessBuilder processBuilder = createProcessBuilder(contextClass, specificationMethod);
        return startProcess(processBuilder);
    }

    private Process startProcess(final ProcessBuilder processBuilder) {
        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private ProcessBuilder createProcessBuilder(final Class<?>... classesToRun) {
        return createProcessBuilder(createCommand(classesToRun));
    }

    private <T> ProcessBuilder createProcessBuilder(final Class<T> contextClassToRun, final String specificationMethod) {
        return createProcessBuilder(createCommand(contextClassToRun, specificationMethod));
    }

    private ProcessBuilder createProcessBuilder(final String[] commandLine) {
        final ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
        final File workingDirectory = new File(getSourceRoot());
        processBuilder.directory(workingDirectory);
        return processBuilder;
    }

    private <T> String[] createCommand(final Class<T> contextClassToRun, final String specificationMethod) {
        final String[] baseCommand = createCommand(contextClassToRun);
        baseCommand[baseCommand.length - 1] += RunnableItemBuilder.METHOD_SEPARATOR + specificationMethod;
        return baseCommand;
    }

    private String[] createCommand(final Class<?>... classesToRun) {
        final List<String> command = new ArrayList<String>();
        command.add(getJavaCommand());
        command.add("-cp");
        command.add(buildClassPath());
        command.add(CommandLineRunner.class.getName());
        for (final Class<?> cls : classesToRun) {
            command.add(cls.getName());
        }
        return command.toArray(new String[command.size()]);
    }

    private String getJavaCommand() {
        return getJavaPath() + "java";
    }

    private String getJavaPath() {
        final String javaHome = System.getenv().get("JAVA_HOME");
        if (javaHome == null) {
            return "";
        } else {
            return javaHome + separatorChar + "bin" + separatorChar;
        }
    }

    private String buildClassPath() {
        final String hamcrest = getJarFilePath(Matchers.class);
        final String clover = getJarFilePath(Clover.class);
        final String jMock = getJarFilePath(ExpectationError.class);
        final String objenesis = getJarFilePath(Objenesis.class);
        final String cgLib = getJarFilePath(CallbackFilter.class);
        return getSourceRoot() + pathSeparatorChar + getTestRoot() + pathSeparatorChar + hamcrest + pathSeparatorChar + clover + pathSeparatorChar +
                jMock + pathSeparatorChar + objenesis + pathSeparatorChar + cgLib + pathSeparatorChar;
    }

    private String getSourceRoot() {
        return getPackageRoot(CommandLineRunner.class);
    }

    private String getTestRoot() {
        return getPackageRoot(CONTEXT_CLASS_1);
    }

    private void expectThatRunnerReportsNoErrors(final byte[] processError) {
        expect.that(new String(processError).trim()).isEmpty();
    }

    private void expectThatRunnerSendsSpeciciationResultsToOutput(final byte[] processOutput, final String specificationName,
            final Class<?>... contextClasses) {
        final String runnerOutput = new String(processOutput);
        for (final Class<?> contextClass : contextClasses) {
            expect.that(runnerOutput).containsString(contextClass.getSimpleName());
        }
        expect.that(runnerOutput).containsString(specificationName);
    }

    private <T> String getJarFilePath(final Class<T> classInJarFile) {
        try {
            final String jarFile = new URL(getPackageRoot(classInJarFile)).getFile();
            return jarFile.substring(0, jarFile.length() - 2);
        } catch (MalformedURLException e) {
            throw new AssertionError(e);
        }
    }

    private <T> String getPackageRoot(final Class<T> classToFindRootOf) {
        return packageRootFinder.getPackageRoot(classToFindRootOf);
    }

    private byte[] read(final InputStream inputStream) {
        return new InputStreamConvertorImpl(inputStream).read();
    }
}
