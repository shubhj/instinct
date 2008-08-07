/*
 * Copyright 2006-2007 Nicholas Partridge
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

import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilder;
import com.googlecode.instinct.internal.runner.SpecificationFailureMessageBuilderImpl;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import static com.googlecode.instinct.internal.util.exception.StringUtil.indentEachLine;
import com.googlecode.instinct.report.ResultMessageBuilder;
import fj.F;
import fj.data.List;
import static fj.data.List.asString;
import static fj.data.List.fromString;
import static fj.data.List.join;
import static java.lang.System.getProperty;

@Suggest("Only print out context name for pending or failed specs. For pending or failed specs print spec names also.")
public final class QuietResultMessageBuilder implements ResultMessageBuilder {
    private static final String NEW_LINE = getProperty("line.separator");
    private final SpecificationFailureMessageBuilder failureMessageBuilder = new SpecificationFailureMessageBuilderImpl();

    public String buildMessage(final ContextResult contextResult) {
        checkNotNull(contextResult);
        if (contextResult.completedSuccessfully()) {
            return "";
        } else {
            return buildFailingContextResultMessage(contextResult);
        }
    }

    public String buildMessage(final SpecificationResult specificationResult) {
        checkNotNull(specificationResult);
        return buildSpecificationResultMessage(specificationResult);
    }

    private String buildFailingContextResultMessage(final ContextResult contextResult) {
        final List<List<Character>> failureMessages = failedSpecifications(contextResult).map(new F<SpecificationResult, List<Character>>() {
            public List<Character> f(final SpecificationResult result) {
                return fromString("- " + buildSpecificationResultMessage(result));
            }
        }).intersperse(fromString(NEW_LINE));
        return contextResult.getContextName() + NEW_LINE + asString(join(failureMessages));
    }

    private String buildSpecificationResultMessage(final SpecificationResult specificationResult) {
        if (specificationResult.completedSuccessfully()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        final String status = " (FAILED)";
        builder.append(specificationResult.getSpecificationName()).append(status);
        builder.append(NEW_LINE).append(NEW_LINE).append(getFailureCause(specificationResult));
        return builder.toString();
    }

    private String getFailureCause(final SpecificationResult specificationResult) {
        final String failureCause = failureMessageBuilder.buildMessage(specificationResult.getStatus());
        return indentEachLine(failureCause);
    }

    private List<SpecificationResult> failedSpecifications(final ContextResult contextResult) {
        return contextResult.getSpecificationResults().filter(new F<SpecificationResult, Boolean>() {
            public Boolean f(final SpecificationResult result) {
                return !result.completedSuccessfully();
            }
        });
    }
}
