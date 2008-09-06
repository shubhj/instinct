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

package com.googlecode.instinct.report;

import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.report.ContextResultsSummary;
import com.googlecode.instinct.internal.runner.ContextResult;
import com.googlecode.instinct.internal.runner.SpecificationResult;

public interface ResultMessageBuilder {
    String buildMessage(final ContextClass contextClass);

    String buildMessage(final ContextResult contextResult);

    String buildMessage(final SpecificationMethod specificationMethod);

    String buildMessage(final SpecificationResult specificationResult);

    String buildMessage(final ContextResultsSummary summary);
}
