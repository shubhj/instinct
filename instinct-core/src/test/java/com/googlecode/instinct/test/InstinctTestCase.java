package com.googlecode.instinct.test;

import static com.googlecode.instinct.mock.Mocker.verify;
import junit.framework.TestCase;

@SuppressWarnings("NoopMethodInAbstractClass")
public abstract class InstinctTestCase extends TestCase {
    @SuppressWarnings({"ProhibitedExceptionDeclared"})
    @Override
    public void runBare() throws Throwable {
        setUpTestDoubles();
        setUpSubject();
        try {
            runTest();
            verify();
        } finally {
            tearDown();
        }
    }

    @Override
    public final void setUp() {
    }

    public void setUpTestDoubles() {
    }

    public void setUpSubject() {
    }
}
