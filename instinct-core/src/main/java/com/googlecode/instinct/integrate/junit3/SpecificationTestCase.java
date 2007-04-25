package com.googlecode.instinct.integrate.junit3;

import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.internal.core.SpecificationMethod;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import junit.framework.TestCase;
import junit.framework.TestResult;

@SuppressWarnings({"JUnitTestCaseInProductSource", "UnconstructableJUnitTestCase", "JUnitTestCaseWithNoTests",
        "JUnitTestCaseWithNonTrivialConstructors"})
public final class SpecificationTestCase extends TestCase {
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final SpecificationRunner specificationRunner = new SpecificationRunnerImpl();
    private TestResult result;
    private final SpecificationMethod specificationMethod;

    public SpecificationTestCase(final SpecificationMethod specificationMethod) {
        super(specificationMethod == null ? "" : specificationMethod.getName());
        checkNotNull(specificationMethod);
        this.specificationMethod = specificationMethod;
    }

    @Override
    public void run(final TestResult result) {
        this.result = result;
        super.run(result);
    }

    @Override
    public void runBare() {
        try {
            runSpecification();
        } catch (EdgeException e) {
            exceptionFinder.rethrowRealError(e);
        }
    }

    @SuppressWarnings({"CatchGenericClass"})
    @Suggest("Run the specification method directly.")
    // SUPPRESS IllegalCatch {
    private void runSpecification() {
        try {
            final SpecificationContext specificationContext = specificationMethod.getSpecificationContext();
            final SpecificationResult specificationResult = specificationRunner.run(specificationContext);
            processSpecificationResult(specificationResult);
        } catch (Throwable e) {
            result.addError(this, e);
        }
    }
    // } SUPPRESS IllegalCatch

    private void processSpecificationResult(final SpecificationResult specificationResult) {
        if (!specificationResult.completedSuccessfully()) {
            final SpecificationRunStatus status = specificationResult.getStatus();
            final Throwable error = (Throwable) status.getDetailedStatus();
            result.addFailure(this, new ChainableAssertionFailedError(exceptionFinder.getRootCause(error)));
        }
    }
}
