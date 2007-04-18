package com.googlecode.instinct.internal.report;

import java.io.ByteArrayOutputStream;
import com.googlecode.instinct.internal.runner.ContextWithSpecificationMethodContainingParameter;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.LifeCycleMethodConfigurationException;
import static com.googlecode.instinct.report.ResultFormat.VERBOSE;
import com.googlecode.instinct.runner.TextContextRunner;
import com.googlecode.instinct.test.InstinctTestCase;

@Suggest("Does this test belong in runner?")
public final class VerboseContextResultMessageBuilderSlowTest extends InstinctTestCase {
    private static final Class<?> CONTEXT_CLASS_WITH_INVALID_SPEC = ContextWithSpecificationMethodContainingParameter.class;
    private TextContextRunner contextRunner;
    private ByteArrayOutputStream output;

    @Override
    public void setUpTestDoubles() {
        output = new ByteArrayOutputStream();
    }

    @Override
    public void setUpSubject() {
        contextRunner = new TextContextRunner(output, VERBOSE);
    }

    public void testDoesNotThrowNullPointerErrorWhenReportingLifecycleConfigurationErrors() {
        contextRunner.run(CONTEXT_CLASS_WITH_INVALID_SPEC);
        checkOutputContainsExpectedConfigurationException();
    }

    private void checkOutputContainsExpectedConfigurationException() {
        final String runnerOutput = new String(output.toByteArray());
        assertTrue(runnerOutput.contains(LifeCycleMethodConfigurationException.class.getSimpleName()));
    }
}
