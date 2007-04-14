package com.googlecode.instinct.integrate.junit3;

import java.lang.reflect.InvocationTargetException;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationResult;
import com.googlecode.instinct.internal.runner.SpecificationRunStatus;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import com.googlecode.instinct.internal.util.ExceptionFinderImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.internal.util.ExceptionFinder;
import junit.framework.TestCase;
import junit.framework.TestResult;

@SuppressWarnings({"JUnitTestCaseInProductSource", "UnconstructableJUnitTestCase", "JUnitTestCaseWithNoTests"})
public final class SpecificationTestCase extends TestCase {
    private final ExceptionFinder exceptionFinder = new ExceptionFinderImpl();
    private final SpecificationRunner specificationRunner;
    private TestResult result;
    private final SpecificationMethod specificationMethod;

    @SuppressWarnings({"JUnitTestCaseWithNonTrivialConstructors"})
    public SpecificationTestCase(final SpecificationMethod specificationMethod) {
        super(specificationMethod == null ? "" : specificationMethod.getName());
        checkNotNull(specificationMethod);
        this.specificationMethod = specificationMethod;
        specificationRunner = new SpecificationRunnerImpl();
    }

    @Override
    public void run(final TestResult result) {
        this.result = result;
        super.run(result);
    }

    @Override
    public void runBare() {
        try {
            runSpecification(result, specificationMethod.getSpecificationContext());
        } catch (EdgeException e) {
            rethrowRealError(e);
        }
    }

    @SuppressWarnings({"CatchGenericClass"})
    // DEBT IllegalCatch {
    private void runSpecification(final TestResult result, final SpecificationContext specificationContext) {
        try {
            final SpecificationResult specificationResult = specificationRunner.run(specificationContext);
            processResult(specificationResult, result);
        } catch (Throwable e) {
            result.addError(this, e);
        }
    }
    // } DEBT IllegalCatch

    private void processResult(final SpecificationResult specificationResult, final TestResult result) {
        if (!specificationResult.completedSuccessfully()) {
            final SpecificationRunStatus status = specificationResult.getStatus();
            final Throwable error = (Throwable) status.getDetailedStatus();
            result.addFailure(this, new ChainableAssertionFailedError(exceptionFinder.getRootCause(error)));
        }
    }

    @Suggest("Do we need to do this elsewhere in the JUnit integration? Method finding, etc.?")
    @SuppressWarnings({"ProhibitedExceptionThrown"})
    private void rethrowRealError(final EdgeException e) {
        // Note. Need to dig down as reflection is pushed behind an edge.
        if (e.getCause() instanceof InvocationTargetException) {
            throw (RuntimeException) e.getCause().getCause();
        } else {
            throw e;
        }
    }
}
