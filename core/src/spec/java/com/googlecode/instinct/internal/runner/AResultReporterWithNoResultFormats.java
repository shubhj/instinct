/*
 * Copyright 2008 Jeremy Mawson
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

package com.googlecode.instinct.internal.runner;

import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.core.ContextClass;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Dummy;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.report.ResultFormat;
import com.googlecode.instinct.runner.ResultReporter;
import fj.data.List;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class AResultReporterWithNoResultFormats {

    @Subject private ResultReporter reporter;
    @Dummy ContextClass contextClass;
    @Dummy ContextResult contextResult;
    @Dummy SpecificationMethod specificationMethod;
    @Dummy SpecificationResult specificationResult;

    @BeforeSpecification
    public void setUp() {
        reporter = new ResultReporterImpl(List.<ResultFormat>nil());
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPreContextRun() {
        reporter.preContextRun(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPostContextRun() {
        reporter.postContextRun(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPreSpecificationMethod() {
        reporter.preSpecificationMethod(null);
    }

    @Specification(expectedException = IllegalArgumentException.class)
    public void willNotAcceptNullParameterInPostSpecificationMethod() {
        reporter.postSpecificationMethod(null);
    }

    @Specification
    public void willDoNothingOnPreContextRun() {
        reporter.preContextRun(contextClass);
    }

    @Specification
    public void willDoNothingOnPostContextRun() {
        reporter.postContextRun(contextResult);
    }

    @Specification
    public void willDoNothingOnPreSpecificationMethod() {
        reporter.preSpecificationMethod(specificationMethod);
    }

    @Specification
    public void willDoNothingOnPostSpecificationMethod() {
        reporter.postSpecificationMethod(specificationResult);
    }
}
