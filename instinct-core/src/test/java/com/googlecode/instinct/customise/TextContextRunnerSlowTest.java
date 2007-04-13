package com.googlecode.instinct.customise;

import com.googlecode.instinct.internal.runner.ASimpleContext;
import com.googlecode.instinct.internal.runner.BehaviourContextRunner;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.test.InstinctTestCase;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class TextContextRunnerSlowTest extends InstinctTestCase {
    private BehaviourContextRunner contextRunner;

    @Suggest("Remove std out, pass a byte array in instead, check bytes appear.")
    public void testContextRunnerLogsToStandardOut() {
        contextRunner.run(ASimpleContext.class);
    }

    @Override
    public void setUpSubject() {
        contextRunner = new TextContextRunner(new BriefContextResultMessageBuilder(), System.out);
    }
}
