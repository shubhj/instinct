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

package com.googlecode.instinct.internal.report;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.ContextClassImpl;
import com.googlecode.instinct.internal.runner.ContextRunner;
import com.googlecode.instinct.internal.runner.ContextWithSpecificationMethodContainingParameter;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.LifeCycleMethodConfigurationException;
import static com.googlecode.instinct.report.ResultFormat.VERBOSE;
import com.googlecode.instinct.runner.TextContextRunner;
import com.googlecode.instinct.test.InstinctTestCase;
import java.io.ByteArrayOutputStream;

@Suggest("Does this test belong in runner?")
public final class VerboseResultMessageBuilderSlowTest extends InstinctTestCase {
    private static final ContextClass CONTEXT_CLASS_WITH_INVALID_SPEC =
            new ContextClassImpl(ContextWithSpecificationMethodContainingParameter.class);
    private ContextRunner contextRunner;
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
