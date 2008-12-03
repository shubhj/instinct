package com.googlecode.instinct.internal.report.html;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public final class ASpecificationResultSummary {

    @Subject
    private SpecificationResultSummary summary;

    @BeforeSpecification
    public void createSubject() {
        summary = new SpecificationResultSummary();
    }

    @Specification
    public void mustNotBeFailedIfFailureTextIsNotSet() {
        summary.setFailureText(null);
        expect.that(summary.getStatus()).isEqualTo(SummaryStatus.passed);
    }

    @Specification
    public void mustBeFailedWhenFailureTextIsSet() {
        summary.setFailureText("spinach");
        expect.that(summary.getStatus()).isEqualTo(SummaryStatus.failed);
    }

    @Specification
    public void mustHaveAFormattedSpecificationName() {
        summary.setSpecificationName(this.getClass().getName());
        expect.that(summary.getFormattedSpecificationName()).isEqualTo("A specification result summary");
    }

    @Specification
    public void mustHaveANullFormattedSpecificationNameWhenTheSpecificationNameIsNull() {
        summary.setSpecificationName(null);
        expect.that(summary.getFormattedSpecificationName()).isNull();
    }

    @Specification
    public void mustHaveEncodedFailureText() {
        summary.setFailureText("< \" & ' >");
        expect.that(summary.getEncodedFailureText()).isEqualTo("&lt; &quot; &amp; &apos; &gt;");
    }

    @Specification
    public void mustHaveANullEncodedFailureTextWhenTheFailureTextIsNull() {
        summary.setFailureText(null);
        expect.that(summary.getEncodedFailureText()).isNull();
    }
}
