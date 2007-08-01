package com.googlecode.instinct.runner;

import java.io.ByteArrayOutputStream;
import com.googlecode.instinct.internal.aggregate.ContextWithSpecsWithDifferentAccessModifiers;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.ContextContainerWithSetUpAndTearDown;
import com.googlecode.instinct.internal.runner.ContextRunner;
import static com.googlecode.instinct.report.ResultFormat.BRIEF;
import static com.googlecode.instinct.runner.TextContextRunner.runContexts;
import com.googlecode.instinct.test.InstinctTestCase;
import static com.googlecode.instinct.test.io.StreamRedirector.doWithRedirectedStandardOut;

public final class TextContextRunnerSlowTest extends InstinctTestCase {
    private ContextRunner contextRunner;
    private ByteArrayOutputStream outputBuffer;

    @Override
    public void setUpTestDoubles() {
        outputBuffer = new ByteArrayOutputStream();
    }

    @Override
    public void setUpSubject() {
        contextRunner = new TextContextRunner(outputBuffer, BRIEF);
    }

    public void testSendsSpeciciationResultsToOutput() {
        checkSendsSpeciciationResultsToOutput(ASimpleContext.class);
        checkSendsSpeciciationResultsToOutput(ContextContainerWithSetUpAndTearDown.class);
    }

    public void testCanBeCalledStaticallySendingResultsToStandardOut() {
        doWithRedirectedStandardOut(outputBuffer, new Runnable() {
            public void run() {
                runContexts(ASimpleContext.class, ContextContainerWithSetUpAndTearDown.class, ContextWithSpecsWithDifferentAccessModifiers.class);
            }
        });
        checkRunnerSendsSpeciciationResultsToOutput(ASimpleContext.class);
        checkRunnerSendsSpeciciationResultsToOutput(ContextContainerWithSetUpAndTearDown.class);
        checkRunnerSendsSpeciciationResultsToOutput(ContextWithSpecsWithDifferentAccessModifiers.class);
    }

    private <T> void checkSendsSpeciciationResultsToOutput(final Class<T> contextClass) {
        contextRunner.run(new ContextClassImpl(contextClass));
        checkRunnerSendsSpeciciationResultsToOutput(contextClass);
    }

    private <T> void checkRunnerSendsSpeciciationResultsToOutput(final Class<T> contextClass) {
        final String runnerOutput = new String(outputBuffer.toByteArray());
        assertTrue("Expected to find context name", runnerOutput.contains(contextClass.getSimpleName()));
    }
}