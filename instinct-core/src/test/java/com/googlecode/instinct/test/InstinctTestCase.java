package com.googlecode.instinct.test;

import junit.framework.TestCase;

@SuppressWarnings("NoopMethodInAbstractClass")
public abstract class InstinctTestCase extends TestCase {
    @Override
    public final void setUp() {
        setUpMocks();
        setUpSubject();
    }

    public void setUpMocks() {
    }

    public void setUpSubject() {
    }
}
