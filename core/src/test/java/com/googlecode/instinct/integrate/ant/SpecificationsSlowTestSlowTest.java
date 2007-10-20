package com.googlecode.instinct.integrate.ant;

import com.googlecode.instinct.test.InstinctTestCase;
import org.apache.tools.ant.Project;

@SuppressWarnings({"LocalVariableOfConcreteClass"})
public final class SpecificationsSlowTestSlowTest extends InstinctTestCase {
    public void testFindsContextsMarkedWithAnnotationsAndMarkedMethods() {
        final Specifications specifications = new Specifications(new Project());
        specifications.setDir(".");
    }
}
