package com.googlecode.instinct.test;

import junit.framework.TestCase;

@SuppressWarnings({"ProhibitedExceptionDeclared", "NoopMethodInAbstractClass"})
public abstract class InstinctTestCase extends TestCase {
    @Override
    public final void setUp() throws Exception {
        setUpMocks();
        setUpSubject();
    }

    public void setUpMocks() {
    }

    public void setUpSubject() {
    }
}
