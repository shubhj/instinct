package com.googlecode.instinct.integrate.junit3;

import java.lang.reflect.InvocationTargetException;
import au.net.netstorm.boost.edge.EdgeException;
import com.googlecode.instinct.internal.runner.SpecificationContext;
import com.googlecode.instinct.internal.runner.SpecificationRunner;
import com.googlecode.instinct.internal.runner.SpecificationRunnerImpl;
import static com.googlecode.instinct.internal.util.ParamChecker.checkNotNull;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.verify.VerificationException;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

@SuppressWarnings({"JUnitTestCaseInProductSource", "UnconstructableJUnitTestCase", "JUnitTestCaseWithNoTests"})
public final class SpecificationTestCase extends TestCase {
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
            handleException(e);
        }
    }

    @SuppressWarnings({"CatchGenericClass"})
    // DEBT IllegalCatch {
    private void runSpecification(final TestResult result, final SpecificationContext specificationContext) {
        try {
            specificationRunner.run(specificationContext);
            System.out.println("specificationContext = " + specificationContext);
        } catch (VerificationException e) {
            result.addFailure(this, new AssertionFailedError(e.getMessage()));
            System.out.println("e = " + e);
        } catch (Throwable e) {
            System.out.println("e = " + e);
            result.addError(this, e);
        }
    }
    // } DEBT IllegalCatch

    @Suggest("Do we need to do this elsewhere in the JUnit integration? Method finding, etc.?")
    @SuppressWarnings({"ProhibitedExceptionThrown"})
    private void handleException(final EdgeException e) {
        // Note. Need to dig down as reflection is pushed behind an edge.
        if (e.getCause() instanceof InvocationTargetException) {
            throw (RuntimeException) e.getCause().getCause();
        } else {
            throw e;
        }
    }
}
