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

import java.io.File;
import static java.io.File.pathSeparatorChar;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import au.net.netstorm.boost.edge.java.io.DefaultEdgeInputStream;
import au.net.netstorm.boost.util.io.DefaultStreamConverter;
import au.net.netstorm.boost.util.nullo.NullMaster;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.internal.aggregate.PackageRootFinder;
import com.googlecode.instinct.internal.aggregate.PackageRootFinderImpl;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.util.Fix;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;
import com.googlecode.instinct.test.TestingException;
import com_cenqua_clover.Clover;
import net.sourceforge.cobertura.coveragedata.CoverageData;

@SuppressWarnings({"HardcodedFileSeparator", "IOResourceOpenedButNotSafelyClosed"})
public final class CommandLineRunnerSlowTest extends InstinctTestCase {
    private static final Class<ASimpleContext> CONTEXT_CLASS_TO_RUN = ASimpleContext.class;
    private final PackageRootFinder packageRootFinder = new PackageRootFinderImpl();
    private final DefaultStreamConverter streamConverter = new DefaultStreamConverter();

    @Suggest("Use the WM written stream convertor, not boost's.")
    @Fix({"Add multiple contexts, to test command line arg merging code."})
    public void testRunsASingleContextFromTheCommandLine() throws IOException {
        final Process process = runContext();
        expectThatRunnerReportsNoErrors(read(process.getErrorStream()));
        expectThatRunnerSendsSpeciciationResultsToOutput(read(process.getInputStream()));
    }

    private Process runContext() throws IOException {
        final ProcessBuilder processBuilder = createProcessBuilder();
        return processBuilder.start();
    }

    private ProcessBuilder createProcessBuilder() {
        final ProcessBuilder processBuilder = new ProcessBuilder(createCommand());
        final File workingDirectory = new File(getSourceRoot());
        processBuilder.directory(workingDirectory);
        return processBuilder;
    }

    @Fix("Remove cobertura, after switching to clover.")
    private String[] createCommand() {
        final String boost = getJarFilePath(NullMaster.class);
        final String clover = getJarFilePath(Clover.class);
        final String cobertura = getJarFilePath(CoverageData.class);
        final String classPath = getSourceRoot() + pathSeparatorChar + getTestRoot() + pathSeparatorChar + boost
                + pathSeparatorChar + clover + pathSeparatorChar + cobertura;
        return new String[]{"java", "-cp", classPath, CommandLineRunner.class.getName(), CONTEXT_CLASS_TO_RUN.getName()};
    }

    private String getSourceRoot() {
        return getPackageRoot(CommandLineRunner.class);
    }

    private String getTestRoot() {
        return getPackageRoot(CONTEXT_CLASS_TO_RUN);
    }

    private void expectThatRunnerReportsNoErrors(final byte[] processError) {
        expect.that(new String(processError).trim()).isEmpty();
    }

    private void expectThatRunnerSendsSpeciciationResultsToOutput(final byte[] processOutput) {
        final String runnerOutput = new String(processOutput);
        assertTrue("Expected to find context name in: '" + runnerOutput + '\'', runnerOutput.contains(CONTEXT_CLASS_TO_RUN.getSimpleName()));
        assertTrue("Expected to find the number of specs run in: '" + runnerOutput + '\'', runnerOutput.contains("Specifications run:"));
    }

    private <T> String getJarFilePath(final Class<T> classInJarFile) {
        try {
            final String jarFile = new URL(getPackageRoot(classInJarFile)).getFile();
            return jarFile.substring(0, jarFile.length() - 2);
        } catch (MalformedURLException e) {
            throw new TestingException(e);
        }
    }

    private <T> String getPackageRoot(final Class<T> classToFindRootOf) {
        return packageRootFinder.getPackageRoot(classToFindRootOf);
    }

    private byte[] read(final InputStream inputStream) {
        return streamConverter.read(new DefaultEdgeInputStream(inputStream));
    }
}
